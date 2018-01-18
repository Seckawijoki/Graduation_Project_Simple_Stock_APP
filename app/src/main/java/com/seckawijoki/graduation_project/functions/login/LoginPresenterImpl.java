package com.seckawijoki.graduation_project.functions.login;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/12 at 15:36.
 */

class LoginPresenterImpl implements LoginContract.Presenter,
        LoginContract.View.ActionCallback,
        LoginContract.Model.DataCallback {
  private LoginContract.View view;
  private LoginContract.Model model;

  @Override
  public LoginContract.Presenter setView(LoginContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public LoginContract.Presenter setModel(LoginContract.Model model) {
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
  public void onRequestLogin(String account, String password, String mac) {
    model.requestLogin(account, password, mac);
  }

  @Override
  public void onRequestAutoLogin(String mac) {
    model.requestAutoLogin(mac);
  }

  @Override
  public void onRequestAccountList() {
    model.requestAccountList();
  }

  @Override
  public void onDisplayLogin(boolean successful, int stringId) {
    view.displayLogin(successful, stringId);
  }

  @Override
  public void onDisplayAutoLogin(String account, String password) {
    view.displayAutoLogin(account, password);
  }

  @Override
  public void onDisplayProgressBar(boolean show) {
    view.displayProgressBar(show);
  }

  @Override
  public void onDisplayAccountError(int stringId) {
    view.displayAccountError(stringId);
  }

  @Override
  public void onDisplayPasswordError(int stringId) {
    view.displayPasswordError(stringId);
  }

  @Override
  public void onDisplayAccountList(List<String> accountList) {
    view.displayAccountList(accountList);
  }
}