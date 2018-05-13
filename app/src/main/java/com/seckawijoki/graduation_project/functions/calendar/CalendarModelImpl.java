package com.seckawijoki.graduation_project.functions.calendar;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/10 at 15:25.
 */

final class CalendarModelImpl implements CalendarContract.Model {
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