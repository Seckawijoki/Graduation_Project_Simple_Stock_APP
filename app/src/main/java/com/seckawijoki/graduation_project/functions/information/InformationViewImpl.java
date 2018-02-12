package com.seckawijoki.graduation_project.functions.information;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentAction;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/8 at 20:08.
 */

final class InformationViewImpl implements InformationContract.View, Toolbar.OnMenuItemClickListener, View.OnClickListener {
  private static final String TAG = "InformationViewImpl";
  private Activity activity;
  private View view;
  private Fragment fragment;
  private ActionCallback callback;
  private ViewPager vp;
  private InformationAdapter adapter;
  @Override
  public void onPresenterInitiate() {
    Toolbar tb = view.findViewById(R.id.tb_information);
    ( (AppCompatActivity) activity ).setSupportActionBar(tb);
    tb.setOnClickListener(this);
    tb.setOnMenuItemClickListener(this);
    String[] titles = activity.getResources().getStringArray(R.array.array_information);
    vp = view.findViewById(R.id.vp_information);
    MagicIndicator indicator = view.findViewById(R.id.indicator_information);

    vp.setOffscreenPageLimit(titles.length <= 3 ? titles.length : 3);
    vp.setAdapter(adapter = new InformationAdapter(fragment.getChildFragmentManager(), activity));
    CommonNavigator commonNavigator = new CommonNavigator(activity);
    commonNavigator.setAdapter(new InformationNavigatorAdapter(titles, vp));
    indicator.setNavigator(commonNavigator);
    ViewPagerHelper.bind(indicator, vp);
  }

  @Override
  public void destroy() {
    activity = null;
    fragment = null;
    callback = null;
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  private InformationViewImpl() {
  }

  InformationViewImpl(Activity activity) {
    this.activity = (AppCompatActivity) activity;
  }

  InformationViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = fragment.getActivity();
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch ( item.getItemId() ) {
      case android.R.id.home:
        Log.d(TAG, "onMenuItemClick()\n: android.R.id.home = " + android.R.id.home);
        adapter.goBack(vp.getCurrentItem());
        break;
      case R.id.menu_push:

        break;
      case R.id.menu_message:
        fragment.startActivity(new Intent(IntentAction.MESSAGE));
        break;
      case R.id.menu_refresh:
        adapter.refreshWeb(vp.getCurrentItem());
        break;
      default:
        return false;
    }
    return true;
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ){
      case R.id.tb_information:
        adapter.reload(vp.getCurrentItem());
        break;
    }
  }
}