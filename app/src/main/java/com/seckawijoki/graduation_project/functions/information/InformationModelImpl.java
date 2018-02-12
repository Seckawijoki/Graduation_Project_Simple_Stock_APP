package com.seckawijoki.graduation_project.functions.information;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/8 at 20:08.
 */

final class InformationModelImpl implements InformationContract.Model {
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