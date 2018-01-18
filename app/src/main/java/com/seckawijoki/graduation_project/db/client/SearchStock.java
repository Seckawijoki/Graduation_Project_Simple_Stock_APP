package com.seckawijoki.graduation_project.db.client;

import org.litepal.crud.DataSupport;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;


/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/3 at 19:21.
 */

public class SearchStock extends DataSupport {
  private long stockTableId;
  private String stockId;
  private String stockName;
  private int  stockType;
  private long searchTime;
  private boolean favorite;
  private boolean searched;
  public long getSearchTime() {
    return searchTime;
  }
  public SearchStock setSearchTime(long searchTime) {
    this.searchTime = searchTime;
    return this;
  }
  public boolean isSearched() {
    return searched;
  }

  public SearchStock setSearched(boolean searched) {
    this.searched = searched;
    return this;
  }

  public long getStockTableId() {
    return stockTableId;
  }

  public SearchStock setStockTableId(long stockTableId) {
    this.stockTableId = stockTableId;
    return this;
  }

  public String getStockId() {
    return stockId;
  }

  public SearchStock setStockId(String stockId) {
    this.stockId = stockId;
    return this;
  }

  public String getStockName() {
    return stockName;
  }

  public SearchStock setStockName(String stockName) {
    this.stockName = stockName;
    return this;
  }

  public int getStockType() {
    return stockType;
  }

  public SearchStock setStockType(int stockType) {
    this.stockType = stockType;
    return this;
  }

  public boolean isFavorite() {
    return favorite;
  }

  public SearchStock setFavorite(boolean favorite) {
    this.favorite = favorite;
    return this;
  }

  @Override
  public String toString() {
    return "\nSearchStock{" +
            "stockId='" + stockId + '\'' +
            ", stockName='" + stockName + '\'' +
            (favorite ? " [favored] " : "") +
            (searched ? " [searched] " : "") +
            '}';
  }
}
