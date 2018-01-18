package com.seckawijoki.graduation_project.db.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/14 at 19:41.
 */

public class MarketStock extends DataSupport{
  private String stockName;
  private double currentPrice;
  private double currentPoint;
  private double fluctuationRate;
  private int rankWeight;
  public MarketStock setJsonArray(JSONArray jsonArray){
    try {
      stockName = jsonArray.getString(0);
      currentPrice = jsonArray.getDouble(1);
      currentPoint = jsonArray.getDouble(2);
      fluctuationRate = jsonArray.getDouble(3);
      rankWeight = jsonArray.getInt(jsonArray.length()-1);
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
    return this;
  }

  public String getStockName() {
    return stockName;
  }

  public MarketStock setStockName(String stockName) {
    this.stockName = stockName;
    return this;
  }

  public double getCurrentPrice() {
    return currentPrice;
  }

  public MarketStock setCurrentPrice(double currentPrice) {
    this.currentPrice = currentPrice;
    return this;
  }

  public double getCurrentPoint() {
    return currentPoint;
  }

  public MarketStock setCurrentPoint(double currentPoint) {
    this.currentPoint = currentPoint;
    return this;
  }

  public double getFluctuationRate() {
    return fluctuationRate;
  }

  public MarketStock setFluctuationRate(double fluctuationRate) {
    this.fluctuationRate = fluctuationRate;
    return this;
  }

  @Override
  public String toString() {
    return "\nMarketStock{" +
            "stockName='" + stockName + '\'' +
            ", currentPrice=" + currentPrice +
            ", currentPoint=" + currentPoint +
            ", fluctuationRate=" + fluctuationRate +
            ", rankWeight=" + rankWeight +
            '}';
  }
}
