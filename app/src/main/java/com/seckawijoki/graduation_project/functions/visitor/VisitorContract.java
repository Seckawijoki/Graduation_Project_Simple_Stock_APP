package com.seckawijoki.graduation_project.functions.visitor;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/23 at 22:37.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

interface VisitorContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    interface ActionCallback {

    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    interface DataCallback {

    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Activity activity);
  }
}
