package com.seckawijoki.graduation_project.functions.market;

import com.seckawijoki.graduation_project.db.client.MarketStock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/14 at 23:44.
 */

interface MarketContract {
  interface View {
    void onModelInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayMarketStocks(List<MarketStock> marketStockList);
    interface ActionCallback {
      void onRequestMarketStocks();
    }
  }

  interface Model {
    void onPresenterInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestMarketStocks();
    interface DataCallback {
      void onDisplayMarketStocks(List<MarketStock> marketStockList);
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
