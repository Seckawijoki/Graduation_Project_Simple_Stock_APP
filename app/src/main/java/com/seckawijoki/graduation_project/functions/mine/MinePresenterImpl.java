package com.seckawijoki.graduation_project.functions.mine;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class MinePresenterImpl implements MineContract.Presenter,
        MineContract.View.ActionCallback,
        MineContract.Model.DataCallback {
  private MineContract.View view;
  private MineContract.Model model;

  @Override
  public void setView(MineContract.View view) {
    this.view = view;
  }

  @Override
  public void setModel(MineContract.Model model) {
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