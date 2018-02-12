package com.seckawijoki.graduation_project.functions.favorite;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/6 at 17:41.
 */

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;

import com.seckawijoki.graduation_project.constants.common.BundleKey;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.functions.quotation_list.QuotationListFragment;

class FavoriteAdapter extends FragmentPagerAdapter implements OnQuotationListRefreshListener {
  private List<QuotationListFragment> fragmentList;
  FavoriteAdapter setFavoriteGroupList(List<FavoriteGroupType> favoriteGroupTypeList) {
    if ( fragmentList == null ) {
      fragmentList = new ArrayList<>(favoriteGroupTypeList.size());
    }
    fragmentList.clear();
    for ( int i = 0 ; i < favoriteGroupTypeList.size() ; i++ ) {
      QuotationListFragment fragment = QuotationListFragment.newInstance(this);
      Bundle bundle = new Bundle();
      bundle.putString(
              BundleKey.FAVORITE_GROUP_NAME,
              favoriteGroupTypeList.get(i).getFavoriteGroupName());
      fragment.setArguments(bundle);
      fragmentList.add(fragment);
    }
    return this;
  }

  FavoriteAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public int getCount() {
    return fragmentList == null ? 0 : fragmentList.size();
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public void onQuotationListRefresh(String groupName) {
    if ( !TextUtils.isEmpty(groupName) ) {
        for ( int i = 0 ; i < fragmentList.size() ; i++ ) {
          QuotationListFragment fragment = fragmentList.get(i);
          if ( TextUtils.equals(
                  fragment.getArguments().getString(BundleKey.FAVORITE_GROUP_NAME),
                  groupName) ) {
            fragment.getMvpView().displayNotifyAdapter();
          } else {
            fragment.getMvpModel().requestStockListFromDatabase();
          }
      }
    } else {
      for ( int i = 0 ; i < fragmentList.size() ; i++ ) {
        QuotationListFragment fragment = fragmentList.get(i);
        fragment.getMvpModel().requestStockListFromDatabase();
      }
    }
  }
}