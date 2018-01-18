package com.seckawijoki.graduation_project.functions.stock;


/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:45.
 */

interface StockContract {
  interface View   {
    void initiate();
    void destroy();
    void setActionCallback(ActionCallback callback);

    interface ActionCallback {

    }
  }

  interface Model   {
    void initiate();
    void destroy();
    void setDataCallback(DataCallback callback);

    interface DataCallback {

    }
  }

  interface Presenter   {
    void initiate();
    void destroy();
    Presenter setView(View view);

    Presenter setModel(Model model);
  }
}
