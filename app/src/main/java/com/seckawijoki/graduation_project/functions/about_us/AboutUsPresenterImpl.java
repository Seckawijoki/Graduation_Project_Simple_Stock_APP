package com.seckawijoki.graduation_project.functions.about_us;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 17:54.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

final class AboutUsPresenterImpl implements AboutUsContract.Presenter,
        AboutUsContract.View.ActionCallback,
        AboutUsContract.Model.DataCallback {
  private AboutUsContract.View view;
  private AboutUsContract.Model model;

  @Override
  public AboutUsContract.Presenter setView(AboutUsContract.View view) {
    this.view = view;
    return this;
  }


  @Override
  public AboutUsContract.Presenter setModel(Activity activity) {
    this.model = new AboutUsModelImpl();
    return this;
  }

  @Override
  public AboutUsContract.Presenter initiate() {
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
  public void onRequestLatestAppVersionCode() {
    model.requestLatestAppVersionCode();
  }

  @Override
  public void onRequestLatestAppVersion() {
    model.requestLatestAppVersion();
  }

  @Override
  public void onRequestLatestApk() {
    model.requestLatestApk();
  }

  @Override
  public void onDisplayLatestAppVersionCode(int versionCode) {
    view.displayLatestAppVersionCode(versionCode);
  }

  @Override
  public void onDisplayLatestAppVersion(int versionCode, String versionName, String versionDescription) {
    view.displayLatestAppVersion(versionCode, versionName, versionDescription);
  }
}