package com.seckawijoki.graduation_project.functions.latest_information;

import com.seckawijoki.graduation_project.db.client.SimpleInformation;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class LatestInformationPresenterImpl implements LatestInformationContract.Presenter,
        LatestInformationContract.View.ActionCallback,
        LatestInformationContract.Model.DataCallback {
  private LatestInformationContract.View view;
  private LatestInformationContract.Model model;

  @Override
  public LatestInformationContract.Presenter setView(LatestInformationContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public LatestInformationContract.Presenter setModel(LatestInformationContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public LatestInformationContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    model.onPresenterInitiate();
    view.onModelInitiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestLatestInformation() {
    model.requestLatestInformation();
  }

  @Override
  public void onDisplayLatestInformation(List<SimpleInformation> informationList) {
    view.displayLatestInformation(informationList);
  }
}