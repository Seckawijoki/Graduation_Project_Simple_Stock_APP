package com.seckawijoki.graduation_project.functions.search;

import com.seckawijoki.graduation_project.db.client.SearchStock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/7 at 20:40.
 */

final class SearchPresenterImpl implements SearchContract.Presenter,
        SearchContract.View.ActionCallback,
        SearchContract.Model.DataCallback {
  private SearchContract.View view;
  private SearchContract.Model model;

  @Override
  public SearchContract.Presenter setView(SearchContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public SearchContract.Presenter setModel(SearchContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public SearchContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.onPresenterInitiate();
    model.onViewInitiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestStockSearchHistory() {
    model.requestStockSearchHistory();
  }

  @Override
  public void onRequestStockSearch(String search) {
    model.requestStockSearch(search);
  }

  @Override
  public void onRequestClearStockSearchHistory() {
    model.requestClearStockSearchHistory();
  }

  @Override
  public void onRequestAddFavoriteStock(SearchStock stock) {
    model.requestAddFavoriteStock(stock);
  }

  @Override
  public void onRequestDeleteFavoriteStock(SearchStock searchStock) {
    model.requestDeleteFavoriteStock(searchStock);
  }


  @Override
  public void onRequestSaveStockSearchHistory(SearchStock searchStock) {
    model.requestSaveStockSearchHistory(searchStock);
  }

  @Override
  public void onDisplayStockSearchHistory(List<SearchStock> stockList) {
    view.displayStockSearchHistory(stockList);
  }

  @Override
  public void onDisplayStockSearch(List<SearchStock> stockList) {
    view.displayStockSearch(stockList);
  }

  @Override
  public void onDisplayClearStockSearchHistory(boolean successful) {
    view.displayClearStockSearchHistory(successful);
  }

  @Override
  public void onDisplayAddFavoriteStock(boolean successful) {
    view.displayAddFavoriteStock(successful);
  }

  @Override
  public void onDisplayDeleteFavoriteStock(boolean successful) {
    view.displayDeleteFavoriteStock(successful);
  }

  @Override
  public void onDisplayNotifyDataSetChange() {
    view.displayNotifyDataSetChange();
  }
}