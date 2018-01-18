package com.seckawijoki.graduation_project.functions.trade;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:51.
 */

interface TradeContract {
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
    Presenter setView(Fragment fragment);
    Presenter setModel(Activity activity);
  }
}
