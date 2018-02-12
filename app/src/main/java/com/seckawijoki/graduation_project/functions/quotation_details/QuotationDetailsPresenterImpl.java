package com.seckawijoki.graduation_project.functions.quotation_details;

import android.app.Activity;

import com.seckawijoki.graduation_project.db.QuotationDetails;
import com.seckawijoki.graduation_project.db.Stock;

import java.io.File;
import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:50.
 */

class QuotationDetailsPresenterImpl implements QuotationDetailsContract.Presenter,
        QuotationDetailsContract.View.ActionCallback,
        QuotationDetailsContract.Model.DataCallback {
  private QuotationDetailsContract.View view;
  private QuotationDetailsContract.Model model;

  @Override
  public QuotationDetailsContract.Presenter setView(QuotationDetailsContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public QuotationDetailsContract.Presenter setModel(QuotationDetailsContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public QuotationDetailsContract.Presenter setModel(Activity activity) {

    return this;
  }

  @Override
  public QuotationDetailsContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    model.initiate();
    view.initiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestUpdateStockList() {
    model.requestUpdateStockList();
  }

  @Override
  public void onRequestQuotationDetails() {
    model.requestQuotationDetails();
  }

  @Override
  public void onRequestKLine(int type) {
    new Thread(()->model.requestKLineChart(type)).start();
  }

  @Override
  public void onRequestAddFavoriteStock() {
    model.requestAddFavoriteStock();
  }

  @Override
  public void onRequestDeleteFavoriteStock() {
    model.requestDeleteFavoriteStock();
  }

  @Override
  public void onDisplayUpdateStockList(List<Stock> stockList) {
    view.displayUpdateStockList(stockList);
  }

  @Override
  public void onDisplayQuotationDetails(QuotationDetails details) {
    view.displayQuotationDetails(details);
  }

  @Override
  public void onDisplayKLineChart(File chartFile) {
    view.displayKLine(chartFile);
  }

  @Override
  public void onDisplayAddFavoriteStock(boolean successful) {
    view.displayAddFavoriteStock(successful);
  }

  @Override
  public void onDisplayDeleteFavoriteStock(boolean successful) {
    view.displayDeleteFavoriteStock(successful);
  }

}