package com.seckawijoki.graduation_project.db.client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;


/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/23 at 19:15.
 */

public class UserTransaction extends DataSupport{
  private long transactionId;
  private int tradeCount;
  private double tradePrice;
  private long tradeDateTime;
  private long userId;
  private String nickname;
  private String portraitUri;
  private long stockTableId;
  private String stockName;
  private String stockId;
  private int stockType;
  public UserTransaction setJsonObject(JSONObject jsonObject){
    try {
      transactionId = jsonObject.getLong(MoJiReTsu.TRANSACTION_ID);
      tradePrice = jsonObject.getDouble(MoJiReTsu.TRADE_PRICE);
      tradeCount = jsonObject.getInt(MoJiReTsu.TRADE_COUNT);
      tradeDateTime = jsonObject.getLong(MoJiReTsu.TRADE_DATE_TIME);
      userId = jsonObject.getLong(MoJiReTsu.USER_ID);
      nickname = jsonObject.getString(MoJiReTsu.NICKNAME);
      stockTableId = jsonObject.getLong(MoJiReTsu.STOCK_TABLE_ID);
      stockName = jsonObject.getString(MoJiReTsu.STOCK_NAME);
      stockId = jsonObject.getString(MoJiReTsu.STOCK_ID);
      stockType = jsonObject.getInt(MoJiReTsu.STOCK_TYPE);
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
    return this;
  }

  public UserTransaction setPortraitUri(String portraitUri) {
    this.portraitUri = portraitUri;
    return this;
  }

  public Bitmap getPortrait(){
    return BitmapFactory.decodeFile(portraitUri);
  }

  public long getUserId() {
    return userId;
  }
  public long getTransactionId() {
    return transactionId;
  }

  public long getStockTableId() {
    return stockTableId;
  }

  public String getNickname() {
    return nickname;
  }

  public String getPortraitUri() {
    return portraitUri;
  }

  public String getStockName() {
    return stockName;
  }

  public String getStockId() {
    return stockId;
  }

  public int getStockType() {
    return stockType;
  }

  public int getTradeCount() {
    return tradeCount;
  }

  public double getTradePrice() {
    return tradePrice;
  }


  public long getTradeDateTime() {
    return tradeDateTime;
  }

  @Override
  public String toString() {
    return "UserTransaction{" +
            "transactionId=" + transactionId +
            ", tradeCount=" + tradeCount +
            ", tradePrice=" + tradePrice +
            ", tradeDateTime=" + tradeDateTime +
            ", nickname='" + nickname + '\'' +
            ", stockName='" + stockName + '\'' +
            ", stockId='" + stockId + '\'' +
            '}';
  }
}
