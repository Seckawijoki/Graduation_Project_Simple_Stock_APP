package com.seckawijoki.graduation_project.functions.search;

import com.seckawijoki.graduation_project.db.client.SearchStock;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/7 at 20:53.
 */

public interface OnSearchStockListener {
  void onSearchStockFavor(SearchStock searchStock, int favorPosition, boolean isHistory);
  void onSearchStockClick(SearchStock searchStock);
  void onSearchStockHistoryClear();
}
