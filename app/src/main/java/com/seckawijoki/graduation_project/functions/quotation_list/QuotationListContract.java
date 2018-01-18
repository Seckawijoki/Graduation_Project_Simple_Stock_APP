package com.seckawijoki.graduation_project.functions.quotation_list;

import com.seckawijoki.graduation_project.db.Stock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:37.
 */

interface QuotationListContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayQuotationList(List<Stock> stockList);
    void displayDeleteFavoriteStock(Stock stock);
    void displaySetFavoriteStockTop(Stock stock);
    void displaySetSpecialAttention(Stock stock);
    void displayNotifyAdapter();
    void displayToast(int msgId);
    interface ActionCallback {
      void onRequestQuotationList();
      void onRequestDeleteFavoriteStock(Stock stock);
      void onRequestSetFavoriteStockTop(Stock stock);
      void onRequestSetSpecialAttention(Stock stock);
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void resume();
    void pause();
    void setDataCallback(DataCallback callback);
    void requestQuotationList();
    void requestDeleteFavoriteStock(Stock stock);
    void requestSetFavoriteStockTop(Stock stock);
    void requestSetSpecialAttention(Stock stock);
    void requestStockListFromDatabase();
    interface DataCallback {
      void onDisplayQuotationList(List<Stock> stockList);
      void onDisplayDeleteFavoriteStock(Stock stock);
      void onDisplaySetFavoriteStockTop(Stock stock);
      void onDisplaySetSpecialAttention(Stock stock);
      void onDisplayToast(int msgId);
    }
  }

  interface Presenter {
    void initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
