package com.seckawijoki.graduation_project.db.server;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/5 at 15:58.
 */

public class AllStocks extends DataSupport {
  private long stockTableId;
  private String stockId;
  private int stockType;
  private String stockName;
  public long getStockTableId() {
    return stockTableId;
  }
  public AllStocks setStockTableId(long stockTableId) {
    this.stockTableId = stockTableId;
    return this;
  }
  public AllStocks setStockId(String stockId) {
    this.stockId = stockId;
    return this;
  }

  public AllStocks setStockName(String stockName) {
    this.stockName = stockName;
    return this;
  }

  public AllStocks setStockType(int stockType) {
    this.stockType = stockType;
    return this;
  }

  @Override
  public String toString() {
    return "\nAllStocks{" +
            "stockTableId=" + stockTableId +
            ", stockId='" + stockId + '\'' +
            ", stockName='" + stockName + '\'' +
            '}';
  }
}
