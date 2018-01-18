package com.seckawijoki.graduation_project.functions.personal_asset;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:58.
 */

final class PersonalAssetViewImpl implements PersonalAssetContract.View {
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

  private PersonalAssetViewImpl() {
  }

  PersonalAssetViewImpl(Activity activity) {
    this.activity = (AppCompatActivity) activity;
  }

  PersonalAssetViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }
}