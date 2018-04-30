package com.seckawijoki.graduation_project.functions.quotation_list;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.server.DefaultGroups;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.db.server.FavoriteStock;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.functions.favorite.OnQuotationListRefreshListener;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.litepal.crud.DataSupport.findFirst;
import static org.litepal.crud.DataSupport.where;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:37.
 */

final class QuotationListModelImpl extends Handler implements QuotationListContract.Model {
  private static final String TAG = QuotationListModelImpl.class.getSimpleName();
  private static final int MSG_WHAT_REQUEST_FROM_DATABASE = 0;
  private OnQuotationListRefreshListener listener;
  private DataCallback callback;
  private Activity activity;
  private String favoriteGroupName;
  private long favoriteGroupId;
  private ExecutorService singleThread = Executors.newFixedThreadPool(1);
  private ScheduledExecutorService cyclicThread = Executors.newSingleThreadScheduledExecutor();
  QuotationListModelImpl(Activity activity, String favoriteGroupName,
                         OnQuotationListRefreshListener listener) {
    this.activity = activity;
    this.favoriteGroupName = favoriteGroupName;
    this.listener = listener;
    favoriteGroupId =
            where("favoriteGroupName = ?", favoriteGroupName)
                    .findFirst(FavoriteGroupType.class)
                    .getFavoriteGroupId();
  }

  @Override
  public void onViewInitiate() {

  }

  @Override
  public void destroy() {
    callback = null;
    singleThread.shutdownNow();
    cyclicThread.shutdownNow();
  }

  @Override
  public void resume() {
    requestQuotations();
  }

  @Override
  public void pause() {
    cyclicThread.shutdown();
  }

  @Override
  public void handleMessage(Message msg) {
    super.handleMessage(msg);
    if ( msg.what == MSG_WHAT_REQUEST_FROM_DATABASE ) {
      requestStockListFromDb();
    }
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestQuotationList() {
    Callable<String> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("userId", GlobalVariableTools.getUserId(activity))
              .add("favoriteGroupId", favoriteGroupId + "")
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.GET_FAVORITE_STOCKS)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      if ( !response.isSuccessful() )
        return null;
      String result = response.body().string();
//      Log.d(TAG, "requestQuotationList(): result = " + result);
      return result;
    };
    Future<String> future = singleThread.submit(callable);
    try {
      String result = future.get();
      JSONArray jsonArray = new JSONArray(result);
      new FavoriteGroupType()
              .setFavoriteGroupId(favoriteGroupId)
              .setFavoriteGroupName(favoriteGroupName)
              .setStockCount(jsonArray.length())
              .saveOrUpdate("favoriteGroupId = ?", favoriteGroupId + "");
      DataSupport.deleteAll(FavoriteStock.class, "favoriteGroupId = ?", favoriteGroupId + "");
      for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
//        Log.d(TAG, "requestQuotationList()\n: jsonObject = " + jsonObject);
        new FavoriteStock()
                .setFavoriteGroupId(favoriteGroupId)
                .setStockTableId(jsonObject.getLong("stockTableId"))
                .setSpecialAttention(jsonObject.getBoolean("specialAttention"))
                .setRankWeight(jsonObject.getInt("rankWeight"))
                .saveOrUpdate("favoriteGroupId = ? and stockTableId = ?",
                        favoriteGroupId + "", jsonObject.getString("stockTableId"));
        /*
                .saveOrUpdate("favoriteGroupId = ? and stockTableId = ?",
                        favoriteGroupId + "",
                        jsonObject.getString("stockTableId"));
        */
      }
      requestQuotations();
    } catch ( InterruptedException | ExecutionException e ) {
      e.printStackTrace();
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }

  private void requestQuotationsFromServer() {
    List<FavoriteStock> favoriteStockList = getFavoriteStockList();
    if ( favoriteStockList.size() <= 0 ) {
      requestStockListFromDatabase();
      return;
    }
    OkHttpUtils.PostBuilder postBuilder = OkHttpUtils.post()
            .url(ServerPath.GET_QUOTATIONS)
            .addParam("userId", GlobalVariableTools.getUserId(activity));
    for ( int i = 0 ; i < favoriteStockList.size() ; ++i ) {
      postBuilder.addParam("stockTableId", favoriteStockList.get(i).getStockTableId() + "");
    }
    JSONArray jsonArray = postBuilder.execute().jsonArray();
    for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
      FavoriteStock favoriteStock = favoriteStockList.get(i);
      try {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        new Stock()
                .setStockTableId(favoriteStock.getStockTableId())
                .setStockId(jsonObject.getString("stockId"))
                .setStockType(jsonObject.getInt("stockType"))
                .setJsonArray(jsonObject.getJSONArray("values"))
                .setSpecialAttention(favoriteStock.isSpecialAttention())
                .setFavorite(true)
                .saveOrUpdate("stockTableId = ?", favoriteStock.getStockTableId() + "");
      } catch ( JSONException e ) {
        Log.e(TAG, "requestQuotations()\n: ", e);
      }
      requestStockListFromDatabase();
      callback.onDisplayToast(R.string.msg_succeed_in_refreshing_quotation_list);
    }
  }

  private void requestQuotations() {
    Runnable runnable = this::requestQuotationsFromServer;
    cyclicThread.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.SECONDS);
  }

  @Override
  public void requestDeleteFavoriteStock(Stock stock){
    if ( TextUtils.equals(favoriteGroupName, DefaultGroups.SPECIAL_ATTENTION) )
      return;
    Callable<String> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("userId", GlobalVariableTools.getUserId(activity))
              .add("favoriteGroupId", favoriteGroupId + "")
              .add("stockTableId", stock.getStockTableId() + "")
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.DELETE_FAVORITE_STOCKS)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      String result = response.body().string();
      return result;
    };
    Future<String> future = singleThread.submit(callable);
    try {
      String result = future.get();
      JSONObject jsonObject = new JSONObject(result);
      JSONArray jsonArray = jsonObject.getJSONArray("stockTableId");
      for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
        String stockTableId = jsonArray.getString(i);
        if (favoriteGroupId != 0) {
          DataSupport.deleteAll(
                  FavoriteStock.class,
                  "favoriteGroupId = ? and stockTableId = ?",
                  favoriteGroupId + "",
                  stockTableId
          );
        } else {
          DataSupport.deleteAll(
                  FavoriteStock.class,
                  "stockTableId = ?",
                  stockTableId
          );
        }
      }
      DataSupport.where("favoriteGroupId = ?", favoriteGroupId + "")
              .findFirst(FavoriteGroupType.class)
              .decreaseStockCount(jsonArray.length())
              .saveOrUpdate("favoriteGroupId = ?", favoriteGroupId + "");
//      callback.onDisplayDeleteFavoriteStock(stock);
      sendEmptyMessage(MSG_WHAT_REQUEST_FROM_DATABASE);
      listener.onQuotationListRefresh(favoriteGroupName);
      callback.onDisplayToast(R.string.msg_succeed_in_deleting);
    } catch ( InterruptedException | ExecutionException | JSONException e ) {
      e.printStackTrace();
      callback.onDisplayToast(R.string.error_failed_to_delete);
    }
  }

  @Override
  public void requestSetFavoriteStockTop(Stock stock) {
    Callable<String> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("userId", GlobalVariableTools.getUserId(activity))
              .add("stockTableId", stock.getStockTableId() + "")
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.SET_FAVORITE_STOCK_TOP)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      return response.body().string();
    };
    Future<String> future = singleThread.submit(callable);
    try {
      String result = future.get();
      JSONObject jsonObject = new JSONObject(result);
      int rankWeight = jsonObject.getInt("rankWeight");
      ContentValues contentValues = new ContentValues();
      contentValues.put("rankWeight", rankWeight);
      DataSupport.updateAll(
              FavoriteStock.class,
              contentValues,
              "favoriteGroupId = ? and stockTableId = ?",
              favoriteGroupId + "",
              stock.getStockTableId() + "");
      callback.onDisplayToast(R.string.msg_succeed_in_setting_top);
      callback.onDisplaySetFavoriteStockTop(stock);
      listener.onQuotationListRefresh(favoriteGroupName);
      requestStockListFromDatabase();
    } catch ( InterruptedException | ExecutionException | JSONException e ) {
      e.printStackTrace();
      callback.onDisplayToast(R.string.error_failed_to_set_top);
    }
  }

  @Override
  public void requestSetSpecialAttention(Stock stock) {
    boolean originIsSpecialAttention = stock.isSpecialAttention();
    Callable<String> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("userId", GlobalVariableTools.getUserId(activity))
              .add("stockTableId", stock.getStockTableId() + "")
              .add("specialAttention", !originIsSpecialAttention + "")
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.SET_SPECIAL_FAVORITE_STOCKS)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      return response.body().string();
    };
    Future<String> future = singleThread.submit(callable);
    try {
      String result = future.get();
      Log.d(TAG, "requestSetSpecialAttention()\n: result = " + result);
      JSONObject jsonObject = new JSONObject(result);
      JSONArray jsonArray = jsonObject.getJSONArray("stockTableId");
      for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
        long stockTableId = jsonArray.getLong(i);
        ContentValues contentValues = new ContentValues();
        contentValues.put("specialAttention", !originIsSpecialAttention);
        DataSupport.updateAll(
                FavoriteStock.class,
                contentValues,
                "stockTableId = ?",
                stock.getStockTableId() + ""
        );
        DataSupport.updateAll(
                Stock.class,
                contentValues,
                "stockTableId = ?",
                stock.getStockTableId() + ""
        );
//        Log.w(TAG, "requestSetSpecialAttention()\n: ");
//        MyLogUtils.logDataSupport(Stock.class);
        DataSupport.where("favoriteGroupId = 1")
                .findFirst(FavoriteGroupType.class)
                .increaseStockCount(originIsSpecialAttention ? -1 : 1)
                .saveOrUpdate("favoriteGroupId = 1");
        if ( favoriteGroupId != 1 ) {
          DataSupport.where("favoriteGroupId = ?", favoriteGroupId + "")
                  .findFirst(FavoriteGroupType.class)
                  .increaseStockCount(originIsSpecialAttention ? -1 : 1)
                  .saveOrUpdate("favoriteGroupId = ?", favoriteGroupId + "");
        }
        if ( originIsSpecialAttention ) {
          DataSupport.deleteAll(
                  FavoriteStock.class,
                  "favoriteGroupId = 1 and stockTableId = ?",
                  stock.getStockTableId() + "");
        } else {
          int minRankWeight = DataSupport.min(FavoriteStock.class,
                  "rankWeight", Integer.class);
          new FavoriteStock()
                  .setFavoriteGroupId(1)
                  .setSpecialAttention(true)
                  .setRankWeight(minRankWeight - 1)
                  .setStockTableId(stockTableId)
                  .save();
        }
      }
      if ( favoriteGroupId == 1 ) {
        callback.onDisplayDeleteFavoriteStock(stock);
      } else {
        callback.onDisplaySetSpecialAttention(stock);
      }
      requestStockListFromDatabase();
      listener.onQuotationListRefresh(favoriteGroupName);
      callback.onDisplayToast(originIsSpecialAttention ?
              R.string.msg_succeed_in_cancelling_special_attention :
              R.string.msg_succeed_in_setting_special_attention);
    } catch ( InterruptedException | ExecutionException | JSONException e ) {
//      Log.e(TAG, "requestSetSpecialAttention()\n: ", e);
      callback.onDisplayToast(originIsSpecialAttention ?
              R.string.error_failed_to_cancel_special_attention :
              R.string.error_failed_to_set_special_attention);
    }
  }

  private List<FavoriteStock> getFavoriteStockList() {
    List<FavoriteStock> favoriteStockList;
    switch ( favoriteGroupName ) {
/*
      case DefaultGroups.ALL:
        favoriteStockList = DataSupport
                .order("rankWeight desc")
                .find(FavoriteStock.class);
        Log.e(TAG, "getFavoriteStockList()\n: favoriteStockList = " + favoriteStockList);
        break;
      case DefaultGroups.SPECIAL_ATTENTION:
        favoriteStockList = DataSupport
                .where("specialAttention = ?", "true")
                .order("rankWeight desc")
                .find(FavoriteStock.class);
        Log.w(TAG, "getFavoriteStockList()\n: favoriteStockList = " + favoriteStockList);
        break;
*/
      default:
        favoriteStockList =
                where("favoriteGroupId = ?", favoriteGroupId + "")
                        .order("rankWeight desc")
                        .find(FavoriteStock.class);
        break;
    }
//    MyLogUtils.logDataSupport(FavoriteStock.class);
//    Log.d(TAG, "getFavoriteStockList()\n: favoriteGroupName = " + favoriteGroupName);
//    Log.d(TAG, "getFavoriteStockList()\n: favoriteGroupId = " + favoriteGroupId);
//    Log.d(TAG, "getFavoriteStockList()\n: favoriteStockList = " + favoriteStockList);
    return favoriteStockList;
  }


  @Override
  public void requestStockListFromDatabase() {
    sendEmptyMessage(MSG_WHAT_REQUEST_FROM_DATABASE);
  }

  private void requestStockListFromDb(){
    List<FavoriteStock> favoriteStockList = getFavoriteStockList();
    List<Stock> stockList = new ArrayList<>();
    for ( int i = 0 ; i < favoriteStockList.size() ; i++ ) {
      FavoriteStock favoriteStock = favoriteStockList.get(i);
      stockList.add(
              where("stockTableId = ?", favoriteStock.getStockTableId() + "")
                      .findFirst(Stock.class)
      );
    }
//    Log.d(TAG, "requestStockListFromDatabase()\n: stockList = " + stockList);
    callback.onDisplayQuotationList(stockList);
  }
}