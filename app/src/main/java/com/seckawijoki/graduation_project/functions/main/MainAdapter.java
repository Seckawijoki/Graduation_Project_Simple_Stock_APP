package com.seckawijoki.graduation_project.functions.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.seckawijoki.graduation_project.functions.assets.AssetsFragment;
import com.seckawijoki.graduation_project.functions.information.InformationFragment;
import com.seckawijoki.graduation_project.functions.mine.MineFragment;
import com.seckawijoki.graduation_project.functions.quotations.QuotationsFragment;
import com.seckawijoki.graduation_project.functions.recommendations.RecommendationsFragment;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/23.
 */

class MainAdapter extends FragmentPagerAdapter {
  private static final String TAG = "MainAdapter";
  MainAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    Log.e(TAG, "getItem: position = " + position);
    switch (position){
      case 0:
        return RecommendationsFragment.getInstance();
      case 1:
        return AssetsFragment.newInstance();
      default:
      case 2:
        return QuotationsFragment.newInstance();
      case 3:
        return InformationFragment.getInstance();
      case 4:
        return MineFragment.getInstance();
    }
  }

  @Override
  public int getCount() {
    return 5;
  }
}
