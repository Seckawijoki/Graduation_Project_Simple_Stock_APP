package com.seckawijoki.graduation_project.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/13 at 16:09.
 */

public class Stock extends DataSupport{
  private static final String TAG = "Stock";
  private long stockTableId;
  String stockId;
  int stockType;
  String stockName;
  /**
   * 当前点数
   * 当前价格与昨日收盘价之差
   */
  double currentPoint;
  double currentPrice;
  double fluctuationRate;
  /**
   * 成交的股票数
   * 单位：股
   * 由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百。
   * 一手为100股
   */
  long volume;
  /**
   * 成交金额
   * 单位为“元”
   * 为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万。
   */
  double turnover;
  /**
   * attributes
   */
  boolean specialAttention;
  boolean favorite;

  public boolean isFavorite() {
    return favorite;
  }

  public Stock setFavorite(boolean favorite) {
    this.favorite = favorite;
    return this;
  }


  public boolean isSpecialAttention() {
    return specialAttention;
  }

  public Stock setSpecialAttention(boolean specialAttention) {
    this.specialAttention = specialAttention;
    return this;
  }

  public long getStockTableId() {
    return stockTableId;
  }
  public Stock setStockTableId(long stockTableId) {
    this.stockTableId = stockTableId;
    return this;
  }
  public Stock setStockType(int stockType) {
    this.stockType = stockType;
    return this;
  }
  public Stock setStockId(String stockId) {
    this.stockId = stockId;
    return this;
  }
  public Stock setValues(String[] values){
    stockName = values[0];
    currentPrice = Float.valueOf(values[1]);
    currentPoint = Float.valueOf(values[2]);
    fluctuationRate = Float.valueOf(values[3]);
    volume = Integer.valueOf(values[4]);
    turnover = Integer.valueOf(values[5]);
    return this;
  }
  public Stock setJsonArray(JSONArray jsonArray) throws JSONException {
//    Log.d(TAG, "setValues()\n: jsonArray = " + jsonArray);
    stockName = jsonArray.optString(0, "");
    currentPrice = jsonArray.optDouble(1, 0);
    currentPoint = jsonArray.optDouble(2, 0);
    fluctuationRate = jsonArray.optDouble(3, 0);
    volume = jsonArray.optInt(4, 0);
    turnover = jsonArray.optInt(5, 0);
    return this;
  }
  public int getStockType() {
    return stockType;
  }


  public String getStockId() {
    return stockId;
  }

  public String getStockName() {
    return stockName;
  }

  public double getCurrentPoint() {
    return currentPoint;
  }

  public double getCurrentPrice() {
    return currentPrice;
  }

  public double getFluctuationRate() {
    return fluctuationRate;
  }

  public long getVolume() {
    return volume;
  }

  public double getVolumeInHundredMillionHands() {
    return volume / 100000000.00;
  }

  public double getTurnover() {
    return turnover;
  }

  public double getTurnoverInHundredMillion() {
    return turnover / 100000000.00;
  }

  @Override
  public boolean equals(Object o) {
    if ( this == o ) return true;
    if ( !( o instanceof Stock ) ) return false;

    Stock stock = (Stock) o;

    return getStockTableId() == stock.getStockTableId();

  }

  @Override
  public int hashCode() {
    return (int) ( getStockTableId() ^ ( getStockTableId() >>> 32 ) );
  }

  @Override
  public String toString() {
    return "\nStock{" +
            "stockTableId=" + stockTableId +
            ", stockId='" + stockId + '\'' +
            ", stockName='" + stockName + '\'' +
            ", specialAttention=" + specialAttention +
            ", favorite=" + favorite +
            '}';
  }
}
