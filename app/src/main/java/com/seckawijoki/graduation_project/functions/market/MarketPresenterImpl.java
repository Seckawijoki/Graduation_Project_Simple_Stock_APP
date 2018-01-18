package com.seckawijoki.graduation_project.functions.market;

import com.seckawijoki.graduation_project.db.client.MarketStock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 13:41.
 */

final class MarketPresenterImpl implements MarketContract.Presenter,
        MarketContract.View.ActionCallback,
        MarketContract.Model.DataCallback {
  private MarketContract.View view;
  private MarketContract.Model model;

  @Override
  public MarketContract.Presenter setView(MarketContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public MarketContract.Presenter setModel(MarketContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public MarketContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    model.onPresenterInitiate();
    view.onModelInitiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestMarketStocks() {
    model.requestMarketStocks();
  }

  @Override
  public void onDisplayMarketStocks(List<MarketStock> marketStockList) {
    view.displayMarketStocks(marketStockList);
  }
}