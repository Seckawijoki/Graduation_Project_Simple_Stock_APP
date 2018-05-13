package com.seckawijoki.graduation_project.functions.information_website;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/13 at 14:36.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

final class InformationWebsitesPresenterImpl implements InformationWebsitesContract.Presenter,
        InformationWebsitesContract.View.ActionCallback,
        InformationWebsitesContract.Model.DataCallback {
  private InformationWebsitesContract.View view;
  private InformationWebsitesContract.Model model;

  @Override
  public InformationWebsitesContract.Presenter setView(InformationWebsitesContract.View view) {
    this.view = view;
    return this;
  }


  @Override
  public InformationWebsitesContract.Presenter setModel(Activity activity) {
    this.model = new InformationWebsitesModelImpl();
    return this;
  }

  @Override
  public InformationWebsitesContract.Presenter initiate() {
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