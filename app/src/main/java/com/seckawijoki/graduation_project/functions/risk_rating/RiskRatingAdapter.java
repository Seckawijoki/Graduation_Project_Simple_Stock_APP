package com.seckawijoki.graduation_project.functions.risk_rating;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/22.
 */

class RiskRatingAdapter extends FragmentPagerAdapter {
  private static final String TAG = "RiskRatingAdapter";
  private OnFragmentChangeListener listener;
  private Fragment layer1Fragment;
  private Fragment layer2Fragment;
  private Fragment layer3Fragment;
  RiskRatingAdapter(FragmentManager fm, OnFragmentChangeListener listener) {
    super(fm);
    this.listener = listener;
    layer1Fragment = Layer1Fragment.getInstance(listener);
    layer2Fragment = Layer2Fragment.getInstance(listener);
    layer3Fragment = Layer3Fragment.getInstance(listener);
  }

  void setChoice(Bundle bundle){
    layer1Fragment.setArguments(bundle);
    layer2Fragment.setArguments(bundle);
    layer3Fragment.setArguments(bundle);
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position){
      default:
      case 0:
        if (layer1Fragment == null){
          layer1Fragment = Layer1Fragment.getInstance(listener);
        }
        return layer1Fragment;
      case 1:
        if (layer2Fragment == null){
          layer2Fragment = Layer2Fragment.getInstance(listener);
        }
        return layer2Fragment;
      case 2:
        if (layer3Fragment == null){
          layer3Fragment = Layer3Fragment.getInstance(listener);
        }
        return layer3Fragment;
    }
  }
}
