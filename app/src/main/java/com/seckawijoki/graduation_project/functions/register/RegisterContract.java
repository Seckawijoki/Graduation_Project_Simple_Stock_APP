package com.seckawijoki.graduation_project.functions.register;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/24 at 15:32.
 */

interface RegisterContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayGetVericode(boolean successful, int msgId);
    void displaySubmitVericode(boolean successful, int msgId);
    void displaySubmitVericode(boolean successful, String msg);
    void displayRegister(boolean successful, int msgId);
    interface ActionCallback {
      void onRequestGetVericode(String country, String phone);
      void onRequestSubmitVericode(String phone, String vericode);
      void onRequestRegister(String phone, String password, String mac);
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestGetVericode(String country, String phone);
    void requestSubmitVericode(String phone, String vericode);
    void requestRegister(String phone, String password, String mac);
    interface DataCallback {
      void onDisplaySubmitVericode(boolean successful, int msgId);
      void onDisplaySubmitVericode(boolean successful, String msg);
      void onDisplayGetVericode(boolean successful, int msgId);
      void onDisplayRegister(boolean successful, int msgId);
    }
  }

  interface Presenter {
    void initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
