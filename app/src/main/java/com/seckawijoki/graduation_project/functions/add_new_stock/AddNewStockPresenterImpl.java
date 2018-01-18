package com.seckawijoki.graduation_project.functions.add_new_stock;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/14 at 12:12.
 */

final class AddNewStockPresenterImpl implements AddNewStockContract.Presenter,
        AddNewStockContract.View.ActionCallback,
        AddNewStockContract.Model.DataCallback {
  private AddNewStockContract.View view;
  private AddNewStockContract.Model model;

  @Override
  public AddNewStockContract.Presenter setView(AddNewStockContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public AddNewStockContract.Presenter setModel(AddNewStockContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public AddNewStockContract.Presenter initiate() {
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
}