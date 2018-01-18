package com.seckawijoki.graduation_project.db.server;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/15 at 15:46.
 */

public class FavoriteStock extends DataSupport {
  private long userId;
  private long favoriteGroupId;
  private long stockTableId;
  private boolean specialAttention;
  private int rankWeight;

  public boolean isSpecialAttention() {
    return specialAttention;
  }

  public FavoriteStock setSpecialAttention(boolean specialAttention) {
    this.specialAttention = specialAttention;
    return this;
  }

  public long getFavoriteGroupId() {
    return favoriteGroupId;
  }
  public FavoriteStock setFavoriteGroupId(long favoriteGroupId) {
    this.favoriteGroupId = favoriteGroupId;
    return this;
  }

  public long getStockTableId() {
    return stockTableId;
  }

  public FavoriteStock setStockTableId(long stockTableId) {
    this.stockTableId = stockTableId;
    return this;
  }

  public int getRankWeight() {
    return rankWeight;
  }
  public FavoriteStock setRankWeight(int rankWeight) {
    this.rankWeight = rankWeight;
    return this;
  }

  @Override
  public String toString() {
    return "\nFavoriteStock{" +
            "favoriteGroupId=" + favoriteGroupId +
            ", stockTableId=" + stockTableId +
            ", specialAttention=" + specialAttention +
            ", rankWeight=" + rankWeight +
            '}';
  }
}
