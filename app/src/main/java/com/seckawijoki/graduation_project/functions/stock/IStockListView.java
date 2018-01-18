package com.seckawijoki.graduation_project.functions.stock;

import com.seckawijoki.graduation_project.db.Stock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/17 at 10:27.
 */

public interface IStockListView {
  void setActionCallback(ActionCallback callback);
  void displayUpdateStockList(List<Stock>stockList);
  interface ActionCallback{
    void onRequestUpdateStockList();
  }
}
