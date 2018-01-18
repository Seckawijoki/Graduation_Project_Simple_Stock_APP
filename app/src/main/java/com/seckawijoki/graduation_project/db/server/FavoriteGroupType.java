package com.seckawijoki.graduation_project.db.server;

import android.text.TextUtils;

import com.seckawijoki.graduation_project.constants.server.DefaultGroups;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/7 at 14:07.
 */

public class FavoriteGroupType extends DataSupport{
  private long favoriteGroupId;
  private String favoriteGroupName;
  private int stockCount;
  private int rankWeight;
  private boolean editable;

  public long getFavoriteGroupId() {
    return favoriteGroupId;
  }

  public FavoriteGroupType setFavoriteGroupId(long favoriteGroupId) {
    this.favoriteGroupId = favoriteGroupId;
    return this;
  }

  public int getStockCount() {
    return stockCount;
  }

  public FavoriteGroupType decreaseStockCount(int decrease){
    stockCount -= decrease;
    return this;
  }

  public FavoriteGroupType increaseStockCount(int decrease){
    stockCount += decrease;
    return this;
  }

  public FavoriteGroupType setStockCount(int stockCount) {
    this.stockCount = stockCount;
    return this;
  }

  public int getRankWeight() {
    return rankWeight;
  }

  public boolean isEditable() {
    return editable;
  }

  public FavoriteGroupType setEditable() {
    return this;
  }

  public String getFavoriteGroupName() {
    return favoriteGroupName;
  }

  public FavoriteGroupType setFavoriteGroupName(String favoriteGroupName) {
    this.favoriteGroupName = favoriteGroupName;
    for ( int i = 0 ; i < DefaultGroups.GROUPS.length ; i++ ) {
      String g = DefaultGroups.GROUPS[i];
      if ( TextUtils.equals(g, favoriteGroupName)) {
        editable = false;
        return this;
      }
    }
    editable = true;
    return this;
  }
  public FavoriteGroupType setRankWeight(int rankWeight) {
    this.rankWeight = rankWeight;
    return this;
  }

  @Override
  public String toString() {
    return new StringBuilder()
            .append(favoriteGroupName)
            .append(" | ")
            .append(rankWeight)
            .append('\n')
            .toString();
  }
}
