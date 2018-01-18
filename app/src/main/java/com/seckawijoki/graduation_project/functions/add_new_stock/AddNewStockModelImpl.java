package com.seckawijoki.graduation_project.functions.add_new_stock;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/14 at 12:12.
 */

final class AddNewStockModelImpl implements AddNewStockContract.Model {
  private DataCallback callback;

  @Override
  public void onViewInitiate() {

  }

  @Override
  public void destroy() {
    callback = null;
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }
}