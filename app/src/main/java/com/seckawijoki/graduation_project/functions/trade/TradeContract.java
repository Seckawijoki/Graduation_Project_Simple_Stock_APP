package com.seckawijoki.graduation_project.functions.trade;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.db.client.UserTransaction;

import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:51.
 */

interface TradeContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayRefreshQuotation(Stock stock);
    void displayKLineChart(File chartFile);
    void displayFollowingTransaction(UserTransaction transaction);
    interface ActionCallback {
      void onRequestRefreshQuotation();
      void onRequestBuying(double tradePrice, int tradeCount);
      void onRequestSelling(double tradePrice, int tradeCount);
      void onRequestKLineChart(int kLineType);
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestRefreshQuotation();
    void requestBuying(double tradePrice, int tradeCount);
    void requestSelling(double tradePrice, int tradeCount);
    void requestKLineChart(int kLineType);
    interface DataCallback {
      void onDisplayRefreshQuotation(Stock stock);
      void onDisplayKLineChart(File chartFile);
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    View getView();
    Presenter setView(Fragment fragment);
    Presenter setModel(Activity activity);
    Model getModel();
  }
}
