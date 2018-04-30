package com.seckawijoki.graduation_project.functions.trade;

import android.app.Activity;
import android.util.Log;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.FilePath;
import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.tools.FileTools;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:51.
 */

final class TradeModelImpl implements TradeContract.Model {
  private static final String TAG = "TradeModelImpl";
  private DataCallback callback;
  private long stockTableId;
  private Activity activity;
  TradeModelImpl(Activity activity) {
    this.activity = activity;
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
  public void requestRefreshQuotation() {
    requestRefreshQuotation(this.stockTableId = GlobalVariableTools.getTradeStockTableId(activity));
  }

  @Override
  public void requestRefreshQuotation(long stockTableId) {
    JSONArray jsonArray = OkHttpUtils.post()
            .addParam(MoJiReTsu.USER_ID, GlobalVariableTools.getUserId(activity))
            .addParam(MoJiReTsu.STOCK_TABLE_ID, stockTableId+"")
            .url(ServerPath.GET_QUOTATIONS)
            .execute()
            .jsonArray();
    try {
      JSONObject jsonObject = jsonArray.getJSONObject(0);
      Stock stock = new Stock()
              .setStockTableId(stockTableId)
              .setStockId(jsonObject.getString(MoJiReTsu.STOCK_ID))
              .setStockType(jsonObject.getInt(MoJiReTsu.STOCK_TYPE))
              .setFavorite(jsonObject.getBoolean(MoJiReTsu.FAVORITE))
              .setJsonArray(jsonObject.getJSONArray(MoJiReTsu.VALUES));
      callback.onDisplayRefreshQuotation(stock);
      stock.saveOrUpdate("stockTableId = ?", stockTableId+"");
    } catch ( JSONException e ) {
      e.printStackTrace();
      callback.onDisplayRefreshQuotation(null);
    }
  }

  @Override
  public void requestBuying(double tradePrice, int tradeCount) {
    JSONObject jsonObject = OkHttpUtils.post()
            .url(ServerPath.ADD_TRANSACTION)
            .addParam(MoJiReTsu.STOCK_TABLE_ID, stockTableId+"")
            .addParam(MoJiReTsu.USER_ID, GlobalVariableTools.getUserId(activity))
            .addParam(MoJiReTsu.TRADE_PRICE, tradePrice+"")
            .addParam(MoJiReTsu.TRADE_COUNT, tradeCount+"")
            .execute()
            .jsonObject();
    try {
      Log.i(TAG, "requestBuying()\n: ");
      boolean result = jsonObject.getBoolean(MoJiReTsu.RESULT);
      if (result){
        ToastUtils.show(activity, R.string.msg_succeed_in_buying);
      } else {
        ToastUtils.show(activity, R.string.error_failed_to_buy);
      }
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }

  @Override
  public void requestSelling(double tradePrice, int tradeCount) {
    JSONObject jsonObject = OkHttpUtils.post()
            .url(ServerPath.ADD_TRANSACTION)
            .addParam(MoJiReTsu.STOCK_TABLE_ID, stockTableId+"")
            .addParam(MoJiReTsu.USER_ID, GlobalVariableTools.getUserId(activity))
            .addParam(MoJiReTsu.TRADE_PRICE, tradePrice+"")
            .addParam(MoJiReTsu.TRADE_COUNT, (-tradeCount)+"")
            .execute()
            .jsonObject();
    try {
      Log.i(TAG, "requestSelling()\n: ");
      boolean result = jsonObject.getBoolean(MoJiReTsu.RESULT);
      if (result){
        ToastUtils.show(activity, R.string.msg_succeed_in_selling);
      } else {
        ToastUtils.show(activity, R.string.error_failed_to_sell);
      }
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }

  private String requestKLineChartFileName(int kLineType) {
    JSONObject jsonObject = OkHttpUtils.post()
            .url(ServerPath.GET_K_LINE_CHART_FILE_NAME)
            .addParam("stockTableId", stockTableId + "")
            .addParam("kLineType", kLineType + "")
            .execute()
            .jsonObject();
    Log.d(TAG, "requestKLineChartFileName()\n: jsonObject = " + jsonObject);
    try {
      return jsonObject.getString("kLineChartFileName");
    } catch ( JSONException e ) {
      Stock stock = DataSupport.where("stockTableId = ?", stockTableId + "")
              .findFirst(Stock.class);
      Log.d(TAG, "requestKLineChartFileName()\n: stock = " + stock);
      if (stock == null){
        return "";
      }
      return FileTools.getKLineChartFileName(
              stock.getStockId(),
              stock.getStockType(),
              kLineType
      );
    }
  }

  @Override
  public void requestKLineChart(int kLineType) {
    File chartFile = OkHttpUtils.getFile()
            .url(ServerPath.GET_K_LINE)
            .fileName(requestKLineChartFileName(kLineType))
            .savePath(FilePath.K_LINE_CHART_PATH)
            .addParam(MoJiReTsu.STOCK_TABLE_ID, stockTableId + "")
            .addParam(MoJiReTsu.K_LINE_TYPE, kLineType + "")
            .executeForFile();
    callback.onDisplayKLineChart(chartFile);
  }
}