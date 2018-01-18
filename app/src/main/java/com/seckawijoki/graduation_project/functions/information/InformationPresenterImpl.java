package com.seckawijoki.graduation_project.functions.information;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class InformationPresenterImpl implements InformationContract.Presenter,
        InformationContract.View.ActionCallback,
        InformationContract.Model.DataCallback {
  private InformationContract.View view;
  private InformationContract.Model model;

  @Override
  public void setView(InformationContract.View view) {
    this.view = view;
  }

  @Override
  public void setModel(InformationContract.Model model) {
    this.model = model;
  }

  @Override
  public void initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.initiate();
    model.initiate();
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }
}