package com.seckawijoki.graduation_project.functions.information;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/8 at 20:08.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

final class InformationPresenterImpl implements InformationContract.Presenter,
        InformationContract.View.ActionCallback,
        InformationContract.Model.DataCallback {
  private InformationContract.View view;
  private InformationContract.Model model;

  @Override
  public InformationContract.Presenter setView(InformationContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public InformationContract.Presenter setView(Fragment fragment) {
    this.view = new InformationViewImpl(fragment);
    return this;
  }


  @Override
  public InformationContract.Presenter setModel(Activity activity) {
    this.model = new InformationModelImpl();
    return this;
  }

  @Override
  public InformationContract.Presenter initiate() {
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