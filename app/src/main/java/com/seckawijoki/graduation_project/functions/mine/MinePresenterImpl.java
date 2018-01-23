package com.seckawijoki.graduation_project.functions.mine;

import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class MinePresenterImpl implements MineContract.Presenter,
        MineContract.View.ActionCallback,
        MineContract.Model.DataCallback {
  private MineContract.View view;
  private MineContract.Model model;

  @Override
  public MineContract.Presenter setView(MineContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public MineContract.Presenter setModel(MineContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public MineContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.initiate();
    model.initiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestUserPortrait() {
    model.requestUserPortrait();
  }

  @Override
  public void onRequestUserInfo() {
    model.requestUserInfo();
  }

  @Override
  public void onDisplayUserPortrait(File portraitFile) {
    view.displayUserPortrait(portraitFile);
  }

  @Override
  public void onDisplayUserInfo(String userId, String nickname) {
    view.displayUserInfo(userId, nickname);
  }
}