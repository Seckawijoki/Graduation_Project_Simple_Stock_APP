package com.seckawijoki.graduation_project.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/11 at 11:07.
 */

public class AccessibleSh extends DataSupport {
  public AccessibleSh setStockId(String stockId) {
    this.stockId = stockId;
    return this;
  }
  public String getStockId() {
    return stockId;
  }
  @Override
  public String toString() {
    return stockId;
  }
  private String stockId;
}
