package com.seckawijoki.graduation_project.functions.recommendations;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class RecommendationsModelImpl implements RecommendationsContract.Model {
  private RecommendationsContract.Model.DataCallback callback;
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