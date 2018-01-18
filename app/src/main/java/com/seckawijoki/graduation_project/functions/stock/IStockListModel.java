package com.seckawijoki.graduation_project.functions.stock;

import com.seckawijoki.graduation_project.db.Stock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/17 at 10:33.
 */

public interface IStockListModel {
  void requestUpdateStockList();
  interface DataCallback{
    void onDisplayUpdateStockList(List<Stock> stockList);
  }
}
