package com.seckawijoki.graduation_project.functions.search;

import android.app.Activity;
import android.content.ContentValues;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.client.SearchStock;
import com.seckawijoki.graduation_project.db.server.FavoriteStock;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/18 at 17:01.
 */

public final class SearchModelImpl implements SearchContract.Model {
  private static final String TAG = "SearchModelImpl";
  private DataCallback callback;
  private Activity activity;
  private long favoriteGroupId;
  private ExecutorService pool = Executors.newFixedThreadPool(3);

  public SearchModelImpl(Activity activity, long favoriteGroupId) {
    this.activity = activity;
    this.favoriteGroupId = favoriteGroupId;
  }

  @Override
  public void onViewInitiate() {

  }

  @Override
  public void destroy() {
    callback = null;
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestStockSearchHistory() {
    Callable<String> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("userId", GlobalVariableTools.getUserId(activity))
              .add("favoriteGroupId", favoriteGroupId+"")
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.GET_STOCK_SEARCH_HISTORY)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      if ( !response.isSuccessful() )
        return null;
      return response.body().string();
    };
    try {
      List<SearchStock> searchStockList = new ArrayList<>();
      Future<String> future = pool.submit(callable);
      String result = future.get();
      JSONArray jsonArray = new JSONArray(result);
      for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        SearchStock searchStock = new SearchStock()
                .setStockTableId(jsonObject.getLong("stockTableId"))
                .setStockId(jsonObject.getString("stockId"))
                .setStockName(jsonObject.getString("stockName"))
                .setStockType(jsonObject.getInt("stockType"))
                .setSearchTime(jsonObject.getLong("searchTime"))
                .setFavorite(jsonObject.getBoolean("favorite"))
                .setSearched(true);
        searchStock.saveOrUpdate(
                "stockTableId = ?",
                jsonObject.getString("stockTableId")
        );
        searchStockList.add(searchStock);
      }
//      Log.d(TAG, "requestStockSearchHistory()\n: searchStockList = " + searchStockList);
      callback.onDisplayStockSearchHistory(searchStockList);
    } catch ( InterruptedException | JSONException | ExecutionException e ) {
      e.printStackTrace();
    }
  }

  @Override
  public void requestStockSearch(String search) {
    JSONArray result = OkHttpUtils.post()
            .url(ServerPath.SEARCH_FOR_MATCHED_STOCKS)
            .addParam("search", search)
            .addParam("userId", GlobalVariableTools.getUserId(activity))
            .addParam("favoriteGroupId", favoriteGroupId+"")
            .execute()
            .jsonArray();
    List<SearchStock> searchStockList = new ArrayList<>();
    for ( int i = 0 ; i < result.length() ; ++i ) {
      try {
        JSONObject jsonObject = result.getJSONObject(i);
        SearchStock searchStock;
        searchStockList.add(searchStock = new SearchStock()
                .setStockTableId(jsonObject.getLong("stockTableId"))
                .setStockId(jsonObject.getString("stockId"))
                .setStockType(jsonObject.optInt("stockType"))
                .setStockName(jsonObject.optString("stockName"))
                .setFavorite(jsonObject.optBoolean("favorite", false)));
        searchStock.saveOrUpdate(
                "stockTableId= ?",
                jsonObject.getString("stockTableId"));
        callback.onDisplayStockSearch(searchStockList);
      } catch ( JSONException e ) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void requestClearStockSearchHistory() {
    Boolean result = OkHttpUtils.post()
            .url(ServerPath.CLEAR_STOCK_SEARCH_HISTORY)
            .addParam("userId", GlobalVariableTools.getUserId(activity))
            .execute()
            .bool();
    List<SearchStock> searchStockList;
    if ( result ) {
      searchStockList = DataSupport
              .where("searched <> 0")
              .find(SearchStock.class);
      for ( int i = 0 ; i < searchStockList.size() ; i++ ) {
        SearchStock searchStock = searchStockList.get(i);
        searchStock.setSearched(false).save();
      }
    }
    searchStockList = DataSupport
            .where("searched <> 0")
            .find(SearchStock.class);
    Log.d(TAG, "requestClearStockSearchHistory()\n: searchStockList = " + searchStockList);
    callback.onDisplayStockSearchHistory(searchStockList);
    callback.onDisplayClearStockSearchHistory(result);
  }

  @Override
  public void requestDeleteFavoriteStock(SearchStock searchStock) {
    Boolean result = OkHttpUtils.post()
            .url(ServerPath.DELETE_FAVORITE_STOCK_FROM_SEARCH)
            .addParam("userId", GlobalVariableTools.getUserId(activity))
            .addParam("stockTableId", searchStock.getStockTableId() + "")
            .addParam("favoriteGroupId", favoriteGroupId+"")
            .execute()
            .bool();
    if ( result ) {
      DataSupport.deleteAll(FavoriteStock.class,
              "stockTableId = ?", searchStock.getStockTableId()+"");
      ContentValues contentValues = new ContentValues();
      contentValues.put("favorite", false);
      DataSupport.updateAll(
              SearchStock.class,
              contentValues,
              "stockTableId = ?", searchStock.getStockTableId()+"");
      callback.onDisplayDeleteFavoriteStock(true);
    } else {
      Log.w(TAG, "requestDeleteFavoriteStock()\n: ");
      callback.onDisplayDeleteFavoriteStock(false);
    }
  }

  @Override
  public void requestSaveStockSearchHistory(SearchStock searchStock) {
    JSONObject result = OkHttpUtils.post()
            .url(ServerPath.SAVE_STOCK_SEARCH_HISTORY)
            .addParam("stockTableId", searchStock.getStockTableId() + "")
            .addParam("userId", GlobalVariableTools.getUserId(activity))
            .execute()
            .jsonObject();
    try {
      long searchTime = result.getLong("searchTime");
      if ( searchTime != 0 ) {
        searchStock
                .setSearched(true)
                .setSearchTime(searchTime)
                .saveOrUpdate("stockTableId = ?", searchStock.getStockTableId() + "");
      }
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }

  @Override
  public void requestAddFavoriteStock(SearchStock searchStock) {
    JSONObject jsonObject = OkHttpUtils.post()
            .url(ServerPath.ADD_FAVORITE_STOCK_FROM_SEARCH)
            .addParam("userId", GlobalVariableTools.getUserId(activity))
            .addParam("favoriteGroupId", favoriteGroupId+"")
            .addParam("stockTableId", searchStock.getStockTableId() + "")
            .execute()
            .jsonObject();
    try {
      new FavoriteStock()
              .setStockTableId(searchStock.getStockTableId())
              .setSpecialAttention(false)
              .setFavoriteGroupId(jsonObject.getLong("favoriteGroupId"))
              .setRankWeight(jsonObject.getInt("rankWeight"))
              .save();
      new FavoriteStock()
              .setStockTableId(searchStock.getStockTableId())
              .setSpecialAttention(false)
              .setFavoriteGroupId(0)
              .setRankWeight(jsonObject.getInt("rankWeight"))
              .save();
      ContentValues contentValues = new ContentValues();
      contentValues.put("favorite", true);
      DataSupport.updateAll(
              SearchStock.class,
              contentValues,
              "stockTableId = ?", searchStock.getStockTableId()+"");
      callback.onDisplayAddFavoriteStock(true);
    } catch ( JSONException e ) {
      Log.w(TAG, "requestAddFavoriteStock()\n: ", e);
      callback.onDisplayAddFavoriteStock(false);
    }
  }



}