package com.seckawijoki.graduation_project.functions.visitor;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/23 at 22:38.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

final class VisitorPresenterImpl implements VisitorContract.Presenter,
        VisitorContract.View.ActionCallback,
        VisitorContract.Model.DataCallback {
  private VisitorContract.View view;
  private VisitorContract.Model model;

  @Override
  public VisitorContract.Presenter setView(VisitorContract.View view) {
    this.view = view;
    return this;
  }



  @Override
  public VisitorContract.Presenter setModel(Activity activity) {
    this.model = new VisitorModelImpl();
    return this;
  }

  @Override
  public VisitorContract.Presenter initiate() {
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