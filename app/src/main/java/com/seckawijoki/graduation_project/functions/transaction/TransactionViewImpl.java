package com.seckawijoki.graduation_project.functions.transaction;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:45.
 */

final class TransactionViewImpl implements TransactionContract.View {
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

  private TransactionViewImpl() {
  }

  TransactionViewImpl(Activity activity) {
    this.activity = (AppCompatActivity) activity;
  }

  TransactionViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }
}