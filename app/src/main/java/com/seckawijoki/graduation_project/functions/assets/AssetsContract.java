package com.seckawijoki.graduation_project.functions.assets;


/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

interface AssetsContract {
  interface View {
    void initiate();
    void destroy();
    void setActionCallback(ActionCallback callback);

    interface ActionCallback {

    }
  }

  interface Model {
    void initiate();
    void destroy();
    void setDataCallback(DataCallback callback);

    interface DataCallback {

    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
