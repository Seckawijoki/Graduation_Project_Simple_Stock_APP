package com.seckawijoki.graduation_project.functions.recommendations;


import com.seckawijoki.graduation_project.db.Stock;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableList;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

public interface RecommendationsContract {
  interface View   {
    void initiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayTopStocks(List<ExpandableGroup> expandableGroupList);
    void displayHotStocks(List<Stock> stockList);
    interface ActionCallback {
      void onRequestTopStocks();
      void onRequestHotStocks();
    }
  }

  interface Model{
    void initiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestTopStocks();
    void requestHotStocks();
    interface DataCallback {
      void onDisplayTopStocks(List<ExpandableGroup> expandableGroupList);
      void onDisplayHotStocks(List<Stock> stockList);
    }
  }

  interface Presenter   {
    void initiate();
    void destroy();
    void setView(View view);

    void setModel(Model model);
  }
}
