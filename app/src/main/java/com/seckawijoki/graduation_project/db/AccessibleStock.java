package com.seckawijoki.graduation_project.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/8 at 19:33.
 */

public class AccessibleStock extends DataSupport {
  private String stockId;
  private int stockType;
  public String getStockId() {
    return stockId;
  }
  public AccessibleStock setStockId(String stockId) {
    this.stockId = stockId;
    return this;
  }
  public int getStockType() {
    return stockType;
  }
  public AccessibleStock setStockType(int stockType) {
    this.stockType = stockType;
    return this;
  }
}
