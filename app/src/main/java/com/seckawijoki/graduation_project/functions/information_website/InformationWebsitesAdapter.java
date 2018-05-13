package com.seckawijoki.graduation_project.functions.information_website;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/8 at 17:49.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.BundleKey;
import com.seckawijoki.graduation_project.functions.simple_web.SimpleWebFragment;

class InformationWebsitesAdapter extends FragmentPagerAdapter {
  private Fragment[] fragments;
  private String[] urls;
  InformationWebsitesAdapter(FragmentManager fm, Context context) {
    super(fm);
    urls = context.getResources().getStringArray(R.array.array_information_websites_url);
    fragments = new Fragment[urls.length];
  }

  void reload(int position) {
    if ( fragments != null && fragments[position] instanceof SimpleWebFragment ) {
      ( (SimpleWebFragment) fragments[position] ).reload();
    }
  }

  void goBack(int position) {
    if ( fragments != null && fragments[position] instanceof SimpleWebFragment ) {
      ( (SimpleWebFragment) fragments[position] ).goBack();
    }
  }

  void refreshWeb(int position) {
    if ( fragments != null && fragments[position] instanceof SimpleWebFragment ) {
      ( (SimpleWebFragment) fragments[position] ).onRefresh();
    }
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    return fragments[position] = (Fragment) super.instantiateItem(container, position);
  }

  @Override
  public int getCount() {
    return urls == null ? 0 : urls.length;
  }

  @Override
  public Fragment getItem(int position) {
    Fragment fragment;
    Bundle bundle = new Bundle();
    bundle.putString(BundleKey.SIMPLE_WEB_URL, urls[position]);
    fragment = SimpleWebFragment.newInstance();
    fragment.setArguments(bundle);
    return fragment;
  }
}