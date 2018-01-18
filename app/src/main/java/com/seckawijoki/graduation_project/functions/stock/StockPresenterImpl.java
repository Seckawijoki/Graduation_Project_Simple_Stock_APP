package com.seckawijoki.graduation_project.functions.stock;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:45.
 */

class StockPresenterImpl implements StockContract.Presenter,
        StockContract.View.ActionCallback,
        StockContract.Model.DataCallback {
  private StockContract.View view;
  private StockContract.Model model;

  @Override
  public StockContract.Presenter setView(StockContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public StockContract.Presenter setModel(StockContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public void initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.initiate();
    model.initiate();
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }
}