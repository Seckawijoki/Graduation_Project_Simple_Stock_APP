package com.seckawijoki.graduation_project.functions.quotation_details;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:50.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.app.FilePath;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.QuotationDetails;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.db.client.SearchStock;
import com.seckawijoki.graduation_project.db.server.FavoriteStock;
import com.seckawijoki.graduation_project.util.FileUtils;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;
import com.seckawijoki.graduation_project.util.OkHttpUtils;
import com.seckawijoki.graduation_project.util.SwitchCaseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Response;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

final class QuotationDetailsModelImpl implements QuotationDetailsContract.Model {
  private static final String TAG = "QuotationDetailsModImpl";
  private Activity activity;
  private List<Stock> stockList = new LinkedList<>();
  private DataCallback callback;
  private long stockTableId;
  private QuotationDetails quotationDetails;
  private ScheduledExecutorService detailsThread = Executors.newSingleThreadScheduledExecutor();
  private ScheduledExecutorService stockListPool = Executors.newSingleThreadScheduledExecutor();

  QuotationDetailsModelImpl(Activity activity, long stockTableId) {
    this.activity = activity;
    this.stockTableId = stockTableId;
  }

  @Override
  public void initiate() {

  }

  @Override
  public void destroy() {
    callback = null;
    detailsThread.shutdownNow();
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestUpdateStockList() {
    Log.d(TAG, "requestUpdateStockList(): ");
//    Runnable runnable = this::updateStockList;
//    stockListPool.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
  }

  private void requestUpdateStockListFromServer() {

  }

  @Override
  public void requestQuotationDetails() {
    Runnable runnable = this::requestQuotationDetailsFromServer;
    detailsThread.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.SECONDS);
  }

  @Override
  public void requestAddFavoriteStock() {
    JSONObject jsonObject = OkHttpUtils.post()
            .url(ServerPath.ADD_FAVORITE_STOCK_FROM_SEARCH)
            .addParam("userId", GlobalVariableUtils.getUserId(activity))
            .addParam("stockTableId", stockTableId + "")
            .execute()
            .jsonObject();
    try {
      new FavoriteStock()
              .setStockTableId(stockTableId)
              .setSpecialAttention(false)
              .setFavoriteGroupId(jsonObject.getLong("favoriteGroupId"))
              .setRankWeight(jsonObject.getInt("rankWeight"))
              .save();
      new FavoriteStock()
              .setStockTableId(stockTableId)
              .setSpecialAttention(false)
              .setFavoriteGroupId(0)
              .setRankWeight(jsonObject.getInt("rankWeight"))
              .save();
      ContentValues contentValues = new ContentValues();
      contentValues.put("favorite", true);
      DataSupport.updateAll(
              SearchStock.class,
              contentValues,
              "stockTableId = ?", stockTableId + "");
      callback.onDisplayAddFavoriteStock(true);
    } catch ( JSONException e ) {
      Log.w(TAG, "requestAddFavoriteStock()\n: ", e);
      callback.onDisplayAddFavoriteStock(false);
    }
  }

  @Override
  public void requestDeleteFavoriteStock() {

    Boolean result = OkHttpUtils.post()
            .url(ServerPath.DELETE_FAVORITE_STOCK_FROM_SEARCH)
            .addParam("userId", GlobalVariableUtils.getUserId(activity))
            .addParam("stockTableId", stockTableId + "")
            .execute()
            .bool();
    if ( result ) {
      DataSupport.deleteAll(FavoriteStock.class,
              "stockTableId = ?", stockTableId + "");
      ContentValues contentValues = new ContentValues();
      contentValues.put("favorite", true);
      DataSupport.updateAll(
              SearchStock.class,
              contentValues,
              "stockTableId = ?", stockTableId + "");
      callback.onDisplayDeleteFavoriteStock(true);
    } else {
      Log.w(TAG, "requestDeleteFavoriteStock()\n: ");
      callback.onDisplayDeleteFavoriteStock(false);
    }
  }

  private void requestQuotationDetailsFromServer() {
    JSONArray jsonArray = OkHttpUtils.post()
            .url(ServerPath.GET_STOCKS)
            .addParam("userId", GlobalVariableUtils.getUserId(activity))
            .addParam("stockTableId", stockTableId + "")
            .execute()
            .jsonArray();
    try {
      JSONObject jsonObject = jsonArray.getJSONObject(0);
      quotationDetails = new QuotationDetails()
              .setStockTableId(stockTableId)
              .setStockId(jsonObject.getString("stockId"))
              .setStockType(jsonObject.getInt("stockType"))
              .setFavorite(jsonObject.getBoolean("favorite"))
              .setJsonArray(jsonObject.getJSONArray("values"));
      quotationDetails.saveOrUpdate("stockTableId = ?", stockTableId + "");
      callback.onDisplayQuotationDetails(quotationDetails);
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }

  private String requestKLineChartFileName(int kLineType){
    JSONObject jsonObject = OkHttpUtils.post()
            .url(ServerPath.GET_K_LINE_CHART_FILE_NAME)
            .addParam("stockTableId", stockTableId+"")
            .addParam("kLineType", kLineType+"")
            .execute()
            .jsonObject();
    try {
      return jsonObject.getString("kLineChartFileName");
    } catch ( JSONException e ) {
      String stockId = quotationDetails.getStockId();
      String sinaKLineType = SwitchCaseUtils.getSinaKLineType(kLineType);
      String sinaStockType = SwitchCaseUtils.getSinaStockType(quotationDetails.getStockType());
      return sinaStockType+stockId+"_"+sinaKLineType+".gif";
    }
  }

  @Override
  public void requestKLine(int kLineType) {
    String fileName = requestKLineChartFileName(kLineType);
    Response response = OkHttpUtils.post()
            .url(ServerPath.GET_K_LINE)
            .addParam("stockTableId", stockTableId+"")
            .addParam("kLineType", kLineType+"")
            .executeForByteStream();
    try {
      InputStream inputStream = response.body().byteStream();
      long total = response.body().contentLength();
       String savePath = FileUtils.isDirectoryExistent(FilePath.K_LINE_CHART_PATH);
      File file = new File(savePath, fileName);
      FileOutputStream fos = new FileOutputStream(file);
      byte[] buf = new byte[2048];
      long sum = 0;
      int len;
      while ( ( len = inputStream.read(buf) ) != -1 ) {
        fos.write(buf, 0, len);
        sum += len;
      }
      fos.flush();
      callback.onDisplayKLine(file);
      Log.d(TAG, "requestKLine()\n: file = " + file);
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }
}