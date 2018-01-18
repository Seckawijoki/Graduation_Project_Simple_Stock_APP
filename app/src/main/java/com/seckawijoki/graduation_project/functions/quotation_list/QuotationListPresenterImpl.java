package com.seckawijoki.graduation_project.functions.quotation_list;

import com.seckawijoki.graduation_project.db.Stock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:37.
 */

final class QuotationListPresenterImpl implements QuotationListContract.Presenter,
        QuotationListContract.View.ActionCallback,
        QuotationListContract.Model.DataCallback {
  private QuotationListContract.View view;
  private QuotationListContract.Model model;

  @Override
  public QuotationListContract.Presenter setView(QuotationListContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public QuotationListContract.Presenter setModel(QuotationListContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public void initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.onPresenterInitiate();
    model.onViewInitiate();
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestQuotationList() {
    model.requestQuotationList();
  }

  @Override
  public void onRequestDeleteFavoriteStock(Stock stock) {
    model.requestDeleteFavoriteStock(stock);
  }

  @Override
  public void onRequestSetFavoriteStockTop(Stock stock) {
    model.requestSetFavoriteStockTop(stock);
  }

  @Override
  public void onRequestSetSpecialAttention(Stock stock) {
    model.requestSetSpecialAttention(stock);
  }

  @Override
  public void onDisplayQuotationList(List<Stock> stockList) {
    view.displayQuotationList(stockList);
  }

  @Override
  public void onDisplayDeleteFavoriteStock(Stock stock) {
    view.displayDeleteFavoriteStock(stock);
  }

  @Override
  public void onDisplaySetFavoriteStockTop(Stock stock) {
    view.displaySetFavoriteStockTop(stock);
  }

  @Override
  public void onDisplaySetSpecialAttention(Stock stock) {
    view.displaySetSpecialAttention(stock);
  }

  @Override
  public void onDisplayToast(int msgId) {
    view.displayToast(msgId);
  }
}