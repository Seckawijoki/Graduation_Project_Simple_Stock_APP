package com.seckawijoki.graduation_project.functions.assets;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.functions.main.ToolbarNavigatorAdapter;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class AssetsViewImpl implements AssetsContract.View, Toolbar.OnMenuItemClickListener {
  private static final String TAG = "AssetsViewImpl";
  private AppCompatActivity activity;
  private Fragment fragment;
  private View view;
  private ActionCallback callback;

  @Override
  public void initiate() {
    Toolbar tb = view.findViewById(R.id.tb_assets);
    activity.setSupportActionBar(tb);
    tb.setOnMenuItemClickListener(this);
    ViewPager vp = view.findViewById(R.id.vp_assets);
    vp.setOffscreenPageLimit(3);
    vp.setAdapter(new AssetsAdapter(fragment.getFragmentManager()).setViewPager(vp));
    MagicIndicator mi = view.findViewById(R.id.indicator_assets);
    CommonNavigator navigator = new CommonNavigator(activity);
    ToolbarNavigatorAdapter adapter = new ToolbarNavigatorAdapter(
            activity.getResources().getStringArray(R.array.array_assets),
            vp
    );
    navigator.setAdapter(adapter);
    mi.setNavigator(navigator);
    ViewPagerHelper.bind(mi, vp);
  }

  @Override
  public void destroy() {
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  private AssetsViewImpl() {
  }
  AssetsViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch ( item.getItemId() ){
      case R.id.menu_search:
        fragment.startActivity(new Intent(IntentAction.SEARCH));
        break;
      case R.id.menu_message:
        fragment.startActivity(new Intent(IntentAction.MESSAGE));
        break;
      case R.id.menu_share:
        ToastUtils.show(activity, R.string.msg_under_developing);
        break;
      case R.id.menu_refresh:
        ToastUtils.show(activity, R.string.msg_under_developing);
        break;
      default:
        return false;
    }
    return true;
  }
}