package com.seckawijoki.graduation_project.functions.login;


import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/12 at 10:11.
 */

interface LoginContract {
  interface View{
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayLogin(boolean successful, int stringId);
    void displayAutoLogin(String account, String password);
    void displayProgressBar(boolean show);
    void displayAccountError(int stringId);
    void displayPasswordError(int stringId);
    void displayAccountList(List<String> accountList);
    interface ActionCallback {
      void onRequestLogin(String account, String password, String mac);
      void onRequestAutoLogin(String mac);
      void onRequestAccountList();
    }
  }

  interface Model{
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestLogin(String account, String password, String mac);
    void requestAutoLogin(String mac);
    void requestAccountList();
    interface DataCallback {
      void onDisplayLogin(boolean successful, int stringId);
      void onDisplayAutoLogin(String account, String password);
      void onDisplayProgressBar(boolean show);
      void onDisplayAccountError(int stringId);
      void onDisplayPasswordError(int stringId);
      void onDisplayAccountList(List<String> accountList);
    }
  }

  interface Presenter {
    void initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
