package com.seckawijoki.graduation_project.functions.visitor;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/23 at 22:38.
 */

final class VisitorModelImpl implements VisitorContract.Model {
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