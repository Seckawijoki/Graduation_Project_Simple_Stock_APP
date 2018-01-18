package com.seckawijoki.graduation_project.functions.assets;


/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class AssetsPresenterImpl implements AssetsContract.Presenter,
        AssetsContract.View.ActionCallback,
        AssetsContract.Model.DataCallback {
  private AssetsContract.View view;
  private AssetsContract.Model model;

  @Override
  public AssetsContract.Presenter setView(AssetsContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public AssetsContract.Presenter setModel(AssetsContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public AssetsContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    model.initiate();
    view.initiate();
    return this;
  }

  @Override
  public void destroy() {

  }
}