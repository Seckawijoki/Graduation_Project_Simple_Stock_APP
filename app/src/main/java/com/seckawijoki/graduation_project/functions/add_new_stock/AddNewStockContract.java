package com.seckawijoki.graduation_project.functions.add_new_stock;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/14 at 12:12.
 */

interface AddNewStockContract {
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
    Presenter setModel(Model model);
  }
}
