package com.seckawijoki.graduation_project.functions.personal_info;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/21 at 19:36.
 */

import android.app.Activity;

interface PersonalInfoContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayUpdatePersonalInfo();
    interface ActionCallback {
      void onRequestUploadPortrait(String filePath);
      void onRequestChangeEmail(String email);
      void onRequestChangeNickname(String nickname);
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestUploadPortrait(String filePath);
    void requestChangeEmail(String email);
    void requestChangeNickname(String nickname);
    interface DataCallback {
      void onDisplayUpdatePersonalInfo();
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Activity activity);
  }
}
