package com.seckawijoki.graduation_project.functions.latest_information;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class LatestInformationModelImpl implements LatestInformationContract.Model {
  private DataCallback callback;

  @Override
  public void onPresenterInitiate() {

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