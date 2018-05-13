package com.seckawijoki.graduation_project.functions.information_website;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/13 at 14:36.
 */

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public final class InformationWebsitesFragment
        extends Fragment
        implements InformationWebsitesContract.View, View.OnClickListener {
  private ActionCallback callback;
  private Activity activity;
  private InformationWebsitesAdapter adapter;
  private ViewPager vp;

  public static InformationWebsitesFragment newInstance() {
    Bundle args = new Bundle();
    InformationWebsitesFragment fragment = new InformationWebsitesFragment();
    fragment.activity = fragment.getActivity();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_information_websites, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();
    View view = getView();
    String[] titles = activity.getResources().getStringArray(R.array.array_information_websites_title);
    vp = view.findViewById(R.id.vp_information_websites);
    vp.setOffscreenPageLimit(titles.length < 3 ? titles.length : 3);
    MagicIndicator mi = view.findViewById(R.id.indicator_information_websites);
    CommonNavigator navigator = new CommonNavigator(activity);
    navigator.setAdapter(new InformationWebsitesNavigatorAdapter(titles, vp));
    mi.setNavigator(navigator);
    ViewPagerHelper.bind(mi, vp);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_information_websites, menu);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case android.R.id.home:
        getActivity().finish();
        break;
      case R.id.menu_refresh:
        adapter.refreshWeb(vp.getCurrentItem());
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onPresenterInitiate() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {

    }
  }

}