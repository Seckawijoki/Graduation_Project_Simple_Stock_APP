package com.seckawijoki.graduation_project.functions.latest_information;


import com.seckawijoki.graduation_project.db.client.SimpleInformation;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

interface LatestInformationContract {
  interface View   {
    void onModelInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayLatestInformation(List<SimpleInformation> informationList);
    interface ActionCallback {
      void onRequestLatestInformation();
    }
  }

  interface Model   {
    void onPresenterInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestLatestInformation();
    interface DataCallback {
      void onDisplayLatestInformation(List<SimpleInformation> informationList);
    }
  }

  interface Presenter   {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
