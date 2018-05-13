package com.seckawijoki.graduation_project.functions.calendar;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/10 at 15:33.
 */

final class CalendarViewImpl implements CalendarContract.View {
  private Activity activity;
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

  private CalendarViewImpl() {
  }

  CalendarViewImpl(Activity activity) {
    this.activity = activity;
  }

  CalendarViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }
}