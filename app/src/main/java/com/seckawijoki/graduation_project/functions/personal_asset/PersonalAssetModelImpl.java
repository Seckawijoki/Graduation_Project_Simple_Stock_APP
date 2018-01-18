package com.seckawijoki.graduation_project.functions.personal_asset;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:58.
 */

final class PersonalAssetModelImpl implements PersonalAssetContract.Model {
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