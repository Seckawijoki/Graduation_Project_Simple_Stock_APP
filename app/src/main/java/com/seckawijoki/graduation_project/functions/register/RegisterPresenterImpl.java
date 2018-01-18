package com.seckawijoki.graduation_project.functions.register;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/24 at 15:32.
 */

class RegisterPresenterImpl implements RegisterContract.Presenter,
        RegisterContract.View.ActionCallback,
        RegisterContract.Model.DataCallback {
  private RegisterContract.View view;
  private RegisterContract.Model model;

  @Override
  public RegisterContract.Presenter setView(RegisterContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public RegisterContract.Presenter setModel(RegisterContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public void initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.onPresenterInitiate();
    model.onViewInitiate();
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestGetVericode(String country, String phone) {
    model.requestGetVericode(country, phone);
  }

  @Override
  public void onRequestSubmitVericode(String phone, String vericode) {
    model.requestSubmitVericode(phone, vericode);
  }

  @Override
  public void onRequestRegister(String password, String mac) {
    model.requestRegister(password, mac);
  }

  @Override
  public void onDisplaySubmitVericode(boolean successful, int msgId) {
    view.displaySubmitVericode(successful, msgId);
  }

  @Override
  public void onDisplaySubmitVericode(boolean successful, String msg) {
    view.displaySubmitVericode(successful, msg);
  }

  @Override
  public void onDisplayGetVericode(boolean successful, int msgId) {
    view.displayGetVericode(successful, msgId);
  }

  @Override
  public void onDisplayRegister(boolean successful, int msgId) {
    view.displayRegister(successful, msgId);
  }
}