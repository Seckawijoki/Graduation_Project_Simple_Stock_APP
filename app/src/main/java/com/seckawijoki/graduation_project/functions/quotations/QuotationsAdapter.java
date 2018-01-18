package com.seckawijoki.graduation_project.functions.quotations;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 9:14.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seckawijoki.graduation_project.functions.favorite.FavoriteFragment;
import com.seckawijoki.graduation_project.functions.market.MarketFragment;

class QuotationsAdapter extends FragmentPagerAdapter {
  private FavoriteFragment favoriteFragment;

  public FavoriteFragment getFavoriteFragment() {
    return favoriteFragment;
  }

  QuotationsAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public int getCount() {
    return 2;
  }

  @Override
  public Fragment getItem(int position) {
    if (position == 0)
      return favoriteFragment = FavoriteFragment.newInstance();
    else
      return MarketFragment.newInstance();
  }
}