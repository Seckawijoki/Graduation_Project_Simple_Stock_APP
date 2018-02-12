package com.seckawijoki.graduation_project.functions.assets;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:59.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.seckawijoki.graduation_project.db.client.UserTransaction;
import com.seckawijoki.graduation_project.functions.personal_asset.PersonalAssetFragment;
import com.seckawijoki.graduation_project.functions.trade.TradeFragment;
import com.seckawijoki.graduation_project.functions.transaction.TransactionFragment;

class AssetsAdapter extends FragmentPagerAdapter implements OnFollowToBuyOrSellListener {
  private TradeFragment tradeFragment;
  private ViewPager vp;
  AssetsAdapter(FragmentManager fm) {
    super(fm);
  }
  public AssetsAdapter setViewPager(ViewPager viewPager) {
    this.vp = viewPager;
    return this;
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
        fragment = TransactionFragment.newInstance(this);break;
      case 1:
        fragment = tradeFragment = TradeFragment.newInstance();break;
      case 2:
        fragment = PersonalAssetFragment.newInstance();break;
    }
    return fragment;
  }

  @Override
  public void onFollowToBuyOrSell(UserTransaction transaction) {
    // TODO: 2018/1/26
    vp.setCurrentItem(1);
    tradeFragment.getPresenter().getView().displayFollowingTransaction(transaction);
  }
}