package com.seckawijoki.graduation_project.functions.calendar;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/10 at 15:33.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

final class CalendarPresenterImpl implements CalendarContract.Presenter,
        CalendarContract.View.ActionCallback,
        CalendarContract.Model.DataCallback {
  private CalendarContract.View view;
  private CalendarContract.Model model;

  @Override
  public CalendarContract.Presenter setView(CalendarContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public CalendarContract.Presenter setView(Fragment fragment) {
    this.view = new CalendarViewImpl(fragment);
    return this;
  }


  @Override
  public CalendarContract.Presenter setModel(Activity activity) {
    this.model = new CalendarModelImpl();
    return this;
  }

  @Override
  public CalendarContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.onPresenterInitiate();
    model.onViewInitiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }
}