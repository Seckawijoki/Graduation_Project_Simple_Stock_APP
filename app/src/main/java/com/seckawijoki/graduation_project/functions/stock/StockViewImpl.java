package com.seckawijoki.graduation_project.functions.stock;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:45.
 */

class StockViewImpl implements StockContract.View {
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

  private StockViewImpl() {
  }

  StockViewImpl(Activity activity) {
    this.activity = activity;
  }

  StockViewImpl(Fragment fragment) {
    this.fragment = fragment;
    activity = fragment.getActivity();
  }
}