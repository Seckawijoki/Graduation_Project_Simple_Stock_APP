package com.seckawijoki.graduation_project.functions.personal_info;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/21 at 19:39.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

final class PersonalInfoPresenterImpl implements PersonalInfoContract.Presenter,
        PersonalInfoContract.View.ActionCallback,
        PersonalInfoContract.Model.DataCallback {
  private PersonalInfoContract.View view;
  private PersonalInfoContract.Model model;

  @Override
  public PersonalInfoContract.Presenter setView(PersonalInfoContract.View view) {
    this.view = view;
    return this;
  }


  @Override
  public PersonalInfoContract.Presenter setModel(Activity activity) {
    this.model = new PersonalInfoModelImpl(activity);
    return this;
  }

  @Override
  public PersonalInfoContract.Presenter initiate() {
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
  public void onRequestUploadPortrait(String filePath) {
    model.requestUploadPortrait(filePath);
  }

  @Override
  public void onRequestChangeEmail(String email) {
    model.requestChangeEmail(email);
  }

  @Override
  public void onRequestChangeNickname(String nickname) {
    model.requestChangeNickname(nickname);
  }

  @Override
  public void onDisplayUpdatePersonalInfo() {
    view.displayUpdatePersonalInfo();
  }
}