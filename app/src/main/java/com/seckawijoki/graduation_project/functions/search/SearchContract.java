package com.seckawijoki.graduation_project.functions.search;

import com.seckawijoki.graduation_project.db.client.SearchStock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/18 at 17:01.
 */

public interface SearchContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayStockSearchHistory(List<SearchStock> stockList);
    void displayStockSearch(List<SearchStock> stockList);
    void displayClearStockSearchHistory(boolean successful);
    void displayAddFavoriteStock(boolean successful);
    void displayDeleteFavoriteStock(boolean successful);
    void displayNotifyDataSetChange();
    interface ActionCallback {
      void onRequestStockSearchHistory();
      void onRequestStockSearch(String search);
      void onRequestClearStockSearchHistory();
      void onRequestAddFavoriteStock(SearchStock searchStock);
      void onRequestDeleteFavoriteStock(SearchStock searchStock);
      void onRequestSaveStockSearchHistory(SearchStock searchStock);
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestStockSearchHistory();
    void requestStockSearch(String search);
    void requestClearStockSearchHistory();
    void requestAddFavoriteStock(SearchStock searchStock);
    void requestDeleteFavoriteStock(SearchStock searchStock);
    void requestSaveStockSearchHistory(SearchStock searchStock);
    interface DataCallback {
      void onDisplayStockSearchHistory(List<SearchStock> stockList);
      void onDisplayStockSearch(List<SearchStock> stockList);
      void onDisplayClearStockSearchHistory(boolean successful);
      void onDisplayAddFavoriteStock(boolean successful);
      void onDisplayDeleteFavoriteStock(boolean successful);
      void onDisplayNotifyDataSetChange();
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
