package com.seckawijoki.graduation_project.functions.recommendations;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class RecommendationsViewImpl implements RecommendationsContract.View {
  private Activity activity;
  private Fragment fragment;
  private ActionCallback callback;

  @Override
  public void initiate() {

  }

  @Override
  public void destroy() {
    activity = null;
    fragment = null;
    callback = null;
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  private RecommendationsViewImpl() {
  }

  RecommendationsViewImpl(Activity activity) {
    this.activity = activity;
  }

  RecommendationsViewImpl(Fragment fragment) {
    this.fragment = fragment;
  }
}