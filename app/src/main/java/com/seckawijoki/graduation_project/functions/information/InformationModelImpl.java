package com.seckawijoki.graduation_project.functions.information;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class InformationModelImpl implements InformationContract.Model {
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