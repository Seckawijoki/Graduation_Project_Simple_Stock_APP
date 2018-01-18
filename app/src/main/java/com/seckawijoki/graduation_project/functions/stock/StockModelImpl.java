package com.seckawijoki.graduation_project.functions.stock;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:45.
 */

class StockModelImpl implements StockContract.Model {
  private DataCallback callback;

  @Override
  public void initiate() {

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