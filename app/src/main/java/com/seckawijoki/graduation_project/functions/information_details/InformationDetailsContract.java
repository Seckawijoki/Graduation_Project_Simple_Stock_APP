package com.seckawijoki.graduation_project.functions.information_details;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/28 at 9:52.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.seckawijoki.graduation_project.db.client.Information;

interface InformationDetailsContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayInformation(Information information);
    interface ActionCallback {
      void onRequestInformation(long informationId);
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestInformation(long informationId);
    interface DataCallback {
      void onDisplayInformation(Information information);
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Activity activity);
  }
}
