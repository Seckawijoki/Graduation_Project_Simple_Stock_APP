package com.seckawijoki.graduation_project.functions.information_website;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/13 at 14:35.
 */

final class InformationWebsitesModelImpl implements InformationWebsitesContract.Model {
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