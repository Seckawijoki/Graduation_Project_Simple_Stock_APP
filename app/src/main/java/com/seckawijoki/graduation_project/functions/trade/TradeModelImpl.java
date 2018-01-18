package com.seckawijoki.graduation_project.functions.trade;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:51.
 */

final class TradeModelImpl implements TradeContract.Model {
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