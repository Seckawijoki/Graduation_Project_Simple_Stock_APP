package com.seckawijoki.graduation_project.functions.personal_asset;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:58.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

final class PersonalAssetPresenterImpl implements PersonalAssetContract.Presenter,
        PersonalAssetContract.View.ActionCallback,
        PersonalAssetContract.Model.DataCallback {
  private PersonalAssetContract.View view;
  private PersonalAssetContract.Model model;

  @Override
  public PersonalAssetContract.Presenter setView(PersonalAssetContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public PersonalAssetContract.Presenter setView(Fragment fragment) {
    this.view = new PersonalAssetViewImpl(fragment);
    return this;
  }


  @Override
  public PersonalAssetContract.Presenter setModel(Activity activity) {
    this.model = new PersonalAssetModelImpl();
    return this;
  }

  @Override
  public PersonalAssetContract.Presenter initiate() {
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