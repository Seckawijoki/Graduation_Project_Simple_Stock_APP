package com.seckawijoki.graduation_project.functions.information;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/8 at 17:49.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.functions.calendar.CalendarFragment;
import com.seckawijoki.graduation_project.functions.latest_information.LatestInformationFragment;

public class InformationAdapter extends FragmentPagerAdapter {
  private Fragment[] fragments = new Fragment[3];
  InformationAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    return fragments[position] = (Fragment) super.instantiateItem(container, position);
  }

  LatestInformationFragment getLatestInformationFragment(){
    return ( (LatestInformationFragment) fragments[0] );
  }

  @Override
  public int getCount() {
    return 1;
  }

  @Override
  public Fragment getItem(int position) {
    Fragment fragment;
    switch ( position ){
      default:case 0:
        fragment = LatestInformationFragment.newInstance();
        break;
      case 1:case 2:
        fragment = CalendarFragment.newInstance();
        break;
    }
    return fragment;
  }
}