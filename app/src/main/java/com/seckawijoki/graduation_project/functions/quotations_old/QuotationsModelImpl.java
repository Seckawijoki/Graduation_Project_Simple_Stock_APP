package com.seckawijoki.graduation_project.functions.quotations_old;

import android.util.Log;

import com.seckawijoki.graduation_project.constants.server.Quotations;
import com.seckawijoki.graduation_project.constants.server.StockType;
import com.seckawijoki.graduation_project.constants.sina.SinaServerPath;
import com.seckawijoki.graduation_project.db.Quotation;
import com.seckawijoki.graduation_project.tools.SinaResponseTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class QuotationsModelImpl implements QuotationsContract.Model {
  private static final String TAG = "QuotationsModelImpl";
  private List<Quotation> quotationList = Collections.synchronizedList(new ArrayList<>(3));
  private DataCallback callback;
  private OkHttpClient okHttpClient = new OkHttpClient();
  private ExecutorService pool = Executors.newFixedThreadPool(3);
  private ScheduledExecutorService cycle = Executors.newSingleThreadScheduledExecutor();
  @Override
  public void initiate() {
  }

  @Override
  public void destroy() {
    callback = null;
    cycle.shutdownNow();
    pool.shutdownNow();
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestUpdateQuotationList() {
    Runnable runnable = () -> {
      try {
        updateQuotation(Quotations.SHANGHAI_COMPOSITE_INDEX, StockType.SH);
        updateQuotation(Quotations.SZSE_COMPOSITE_INDEX, StockType.SZ);
        updateQuotation(Quotations.GROWTH_ENTERPRISES_MARKET, StockType.SZ);
//      callback.onDisplayUpdateStockList(quotationList);
//        Log.d(TAG, "requestUpdateStockList(): quotationList = " + quotationList);
      } catch ( NullPointerException e ) {
        Log.e(TAG, "requestUpdateStockList(): " + e);
      }
    };
    cycle.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
  }

  private void updateQuotation(final String stockId, final int stockType){
    Callable<Quotation> callable = () -> {
      try {
        String url = String.format(SinaServerPath.GET_MARKET_STOCK_FORMAT, stockType, stockId);
        Request request = new Request.Builder()
                .url(url).get().build();
//        Log.d(TAG, "updateQuotation(): url = " + url);
        Response response = okHttpClient.newCall(request).execute();
        while ( !response.isSuccessful() ) {
        }
        String result = response.body().string();
//        Log.d(TAG, "updateQuotation(): result = " + result);
        String[] values = SinaResponseTools.parse(result);
        Quotation quotation = new Quotation()
                .setQuotationId(stockId)
                .setQuotationType(stockType)
                .setValues(values);
        quotation.saveOrUpdate(Quotation.QUOTATION_ID + "=?", stockId);
//        Log.d(TAG, "updateQuotation(): quotation = " + quotation);
        return quotation;
      } catch ( IOException e ) {
        e.printStackTrace();
      }
      return null;
    };
    Future<Quotation> future = pool.submit(callable);
    try {
      Quotation quotation = future.get();
//      Log.d(TAG, "updateQuotation(): quotation = " + quotation);
      int position;
      if ( ( position = quotationList.indexOf(quotation) ) >= 0 ) {
        quotationList.set(position, quotation);
      } else {
        quotationList.add(quotation);
      }
      callback.onDisplayUpdateQuotationList(quotationList);
//      Log.d(TAG, "updateQuotation(): quotationList = " + quotationList);
    } catch ( InterruptedException | ExecutionException e ) {
      e.printStackTrace();
    }
  }

}