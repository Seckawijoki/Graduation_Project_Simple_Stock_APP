package com.seckawijoki.graduation_project.functions.about_us;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 17:54.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

interface AboutUsContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayLatestAppVersionCode(int versionCode);
    void displayLatestAppVersion(int versionCode, String versionName, String versionDescription);
    interface ActionCallback {
      void onRequestLatestAppVersionCode();
      void onRequestLatestAppVersion();
      void onRequestLatestApk();
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestLatestAppVersionCode();
    void requestLatestAppVersion();
    void requestLatestApk();
    interface DataCallback {
      void onDisplayLatestAppVersionCode(int versionCode);
      void onDisplayLatestAppVersion(int versionCode, String versionName, String versionDescription);
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Activity activity);
  }
}
