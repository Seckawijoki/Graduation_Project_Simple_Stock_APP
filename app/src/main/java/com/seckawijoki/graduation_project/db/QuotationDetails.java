package com.seckawijoki.graduation_project.db;

import com.seckawijoki.graduation_project.util.SinaStockUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/17 at 9:53.
 */

public class QuotationDetails extends Stock {
  private double openingPriceToday;
  private double closingPriceYesterday;
  private double highestPriceToday;
  private double lowestPriceToday;
  private Date dateTime;
  private Time clockTime;

  @Override
  public QuotationDetails setFavorite(boolean favorite) {
    super.setFavorite(favorite);
    return this;
  }

  @Override
  public QuotationDetails setStockTableId(long stockTableId) {
    super.setStockTableId(stockTableId);
    return this;
  }

  @Override
  public QuotationDetails setStockType(int stockType) {
    super.setStockType(stockType);
    return this;
  }

  @Override
  public QuotationDetails setStockId(String stockId) {
    super.setStockId(stockId);
    return this;
  }

  public double getAveragePrice() {
    return ( highestPriceToday + lowestPriceToday ) / 2;
  }

  public double getPriceDifference() {
    return currentPrice - closingPriceYesterday;
  }

  public double getPriceDifferenceRate() {
    return getPriceDifference() / closingPriceYesterday * 100;
  }

  @Override
  public QuotationDetails setJsonArray(JSONArray jsonArray){
    try {
      stockName = jsonArray.getString(0);
      openingPriceToday = jsonArray.getDouble(1);
      closingPriceYesterday = jsonArray.getDouble(2);
      currentPrice = jsonArray.getDouble(3);
      highestPriceToday = jsonArray.getDouble(4);
      lowestPriceToday = jsonArray.getDouble(5);
      //TODO: might need saving
      volume = jsonArray.getLong(8);
      turnover = jsonArray.getDouble(9);
      //TODO: might need saving
      dateTime = Date.valueOf(jsonArray.getString(30));
      clockTime = Time.valueOf(jsonArray.getString(31));
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
    return this;
  }

  public QuotationDetails setResponseResult(String result) {
    String[] values = SinaStockUtils.parse(result);
    stockName = values[0];
    openingPriceToday = Float.valueOf(values[1]);
    closingPriceYesterday = Float.valueOf(values[2]);
    currentPrice = Float.valueOf(values[3]);
    highestPriceToday = Float.valueOf(values[4]);
    lowestPriceToday = Float.valueOf(values[5]);
    //TODO: might need saving
    volume = Long.valueOf(values[8]);
    turnover = Integer.valueOf(values[9]);
    //TODO: might need saving
    dateTime = Date.valueOf(values[30]);
    clockTime = Time.valueOf(values[31]);
    fluctuationRate = ( highestPriceToday - lowestPriceToday ) / openingPriceToday * 100;
    return this;
  }

  public double getOpeningPriceToday() {
    return openingPriceToday;
  }

  public double getClosingPriceYesterday() {
    return closingPriceYesterday;
  }

  public double getHighestPriceToday() {
    return highestPriceToday;
  }

  public double getLowestPriceToday() {
    return lowestPriceToday;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public Time getClockTime() {
    return clockTime;
  }
  /**
   这个字符串由许多数据拼接在一起，不同含义的数据用逗号隔开了，按照程序员的思路，顺序号从0开始。
   0：”大秦铁路”，股票名字；
   1：”27.55″，今日开盘价；
   2：”27.25″，昨日收盘价；
   3：”26.91″，当前价格；
   4：”27.55″，今日最高价；
   5：”26.20″，今日最低价；
   6：”26.91″，竞买价，即“买一”报价；
   7：”26.92″，竞卖价，即“卖一”报价；
   8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
   9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
   10：”4695″，“买一”申请4695股，即47手；
   11：”26.91″，“买一”报价；
   12：”57590″，“买二”
   13：”26.90″，“买二”
   14：”14700″，“买三”
   15：”26.89″，“买三”
   16：”14300″，“买四”
   17：”26.88″，“买四”
   18：”15100″，“买五”
   19：”26.87″，“买五”
   20：”3100″，“卖一”申报3100股，即31手；
   21：”26.92″，“卖一”报价
   (22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
   30：”2008-01-11″，日期；
   31：”15:05:32″，时间；
   */
}
