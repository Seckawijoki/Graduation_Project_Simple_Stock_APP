package com.seckawijoki.graduation_project.functions.market;

import android.util.Log;

import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.client.MarketStock;
import com.seckawijoki.graduation_project.util.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 12:56.
 */

final class MarketModelImpl implements MarketContract.Model {
  private static final String TAG = "MarketModelImpl";
  private DataCallback callback;
  private ScheduledExecutorService cycleThread = Executors.newSingleThreadScheduledExecutor();
  @Override
  public void onPresenterInitiate() {

  }

  @Override
  public void destroy() {
    callback = null;
    cycleThread.shutdownNow();
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  private void requestMarketStocksFromServer(){
    JSONArray jsonArray = OkHttpUtils.get()
            .url(ServerPath.GET_FOREIGN_STOCKS)
            .execute()
            .jsonArray();
    for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
      try {
        JSONArray ja = jsonArray.getJSONArray(i);
        new MarketStock()
                .setJsonArray(ja)
                .saveOrUpdate("stockName = ?", ja.getString(0));
      } catch ( JSONException e ) {
        e.printStackTrace();
      }
    }
    List<MarketStock> marketStockList = DataSupport
            .order("rankWeight desc")
            .find(MarketStock.class);
    Log.d(TAG, "requestMarketStocksFromServer()\n: marketStockList = " + marketStockList);
    callback.onDisplayMarketStocks(marketStockList);
  }

  @Override
  public void requestMarketStocks() {
    Runnable runnable = this::requestMarketStocksFromServer;
    cycleThread.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.SECONDS);
  }
}