package com.seckawijoki.graduation_project.functions.latest_information;


/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

interface LatestInformationContract {
  interface View   {
    void onModelInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);

    interface ActionCallback {

    }
  }

  interface Model   {
    void onPresenterInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    interface DataCallback {

    }
  }

  interface Presenter   {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
