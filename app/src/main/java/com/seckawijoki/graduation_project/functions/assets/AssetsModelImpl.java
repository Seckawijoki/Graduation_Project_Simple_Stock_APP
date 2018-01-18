package com.seckawijoki.graduation_project.functions.assets;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class AssetsModelImpl implements AssetsContract.Model {
  private DataCallback callback;

  @Override
  public void initiate() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }
}