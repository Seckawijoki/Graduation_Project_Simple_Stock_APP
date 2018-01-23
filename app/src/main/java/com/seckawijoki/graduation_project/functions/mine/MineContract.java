package com.seckawijoki.graduation_project.functions.mine;


import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

interface MineContract {
  interface View   {
    void initiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayShowUserInfo(boolean show);
    void displayUserPortrait(File portraitFile);
    void displayUserInfo(String userId, String nickname);
    interface ActionCallback {
      void onRequestUserPortrait();
      void onRequestUserInfo();
    }
  }

  interface Model   {
    void initiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestUserPortrait();
    void requestUserInfo();
    interface DataCallback {
      void onDisplayUserPortrait(File portraitFile);
      void onDisplayUserInfo(String userId, String nickname);
    }
  }

  interface Presenter   {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);

  }
}
