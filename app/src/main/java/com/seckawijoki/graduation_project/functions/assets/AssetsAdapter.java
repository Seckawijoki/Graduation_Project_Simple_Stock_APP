package com.seckawijoki.graduation_project.functions.assets;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:59.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seckawijoki.graduation_project.functions.personal_asset.PersonalAssetFragment;
import com.seckawijoki.graduation_project.functions.trade.TradeFragment;
import com.seckawijoki.graduation_project.functions.transaction.TransactionFragment;

class AssetsAdapter extends FragmentPagerAdapter {
  AssetsAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public Fragment getItem(int position) {
    Fragment fragment;
    switch ( position ){
      default:case 0:
        fragment = TransactionFragment.newInstance();break;
      case 1:
        fragment = TradeFragment.newInstance();break;
      case 2:
        fragment = PersonalAssetFragment.newInstance();break;
    }
    return fragment;
  }
}