package com.seckawijoki.graduation_project.functions.trade;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:52.
 */

final class TradeViewImpl implements TradeContract.View {
  private AppCompatActivity activity;
  private View view;
  private Fragment fragment;
  private ActionCallback callback;

  @Override
  public void onPresenterInitiate() {

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

  private TradeViewImpl() {
  }

  TradeViewImpl(AppCompatActivity activity) {
    this.activity = activity;
  }

  TradeViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }
}