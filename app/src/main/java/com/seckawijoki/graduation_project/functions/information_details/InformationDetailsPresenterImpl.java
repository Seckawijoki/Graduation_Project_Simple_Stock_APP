package com.seckawijoki.graduation_project.functions.information_details;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/28 at 9:52.
 */

import android.app.Activity;

import com.seckawijoki.graduation_project.db.client.Information;

final class InformationDetailsPresenterImpl implements InformationDetailsContract.Presenter,
        InformationDetailsContract.View.ActionCallback,
        InformationDetailsContract.Model.DataCallback {
  private InformationDetailsContract.View view;
  private InformationDetailsContract.Model model;

  @Override
  public InformationDetailsContract.Presenter setView(InformationDetailsContract.View view) {
    this.view = view;
    return this;
  }


  @Override
  public InformationDetailsContract.Presenter setModel(Activity activity) {
    this.model = new InformationDetailsModelImpl();
    return this;
  }

  @Override
  public InformationDetailsContract.Presenter initiate() {
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

  @Override
  public void onRequestInformation(long informationId) {
    model.requestInformation(informationId);
  }

  @Override
  public void onDisplayInformation(Information information) {
    view.displayInformation(information);
  }
}