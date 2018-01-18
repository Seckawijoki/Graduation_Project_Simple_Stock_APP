package com.seckawijoki.graduation_project.functions.TEST;


/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/25.
 */

interface TESTContract {
  interface View   {
    void initiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    interface ActionCallback {
      void onRequestConnectToServer();
      void onRequestNormalDownload(CharSequence s);
      void onRequestGetSzIdAndName();
    }
    void displaySendEditText( CharSequence s);
    void displayReceptionEditText( CharSequence s);
  }

  interface Model   {
    void initiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    interface DataCallback {
      void onDisplaySendEditText( CharSequence s);
      void onDisplayReceptionEditText( CharSequence s);
    }
    void requestConnectToServer();
    void requestNormalDownload(CharSequence s);
    void requestGetSzIdAndName();
  }

  interface Presenter   {
    void initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
