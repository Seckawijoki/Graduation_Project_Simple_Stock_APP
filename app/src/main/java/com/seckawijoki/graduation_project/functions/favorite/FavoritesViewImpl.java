package com.seckawijoki.graduation_project.functions.favorite;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.ViewUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:31.
 */

final class FavoritesViewImpl implements FavoriteContract.View, View.OnClickListener {
  private static final String TAG = "FavoritesViewImpl";
  private AppCompatActivity activity;
  private View view;
  private Fragment fragment;
  private ActionCallback callback;
  private MagicIndicator indicator;
  private ViewPager vp;
  private FavoriteAdapter vpAdapter;

  public FavoriteAdapter getFavoriteAdapter() {
    return vpAdapter;
  }

  @Override
  public void onPresenterInitiate() {
    /*
    defaultList.add("全部");
    defaultList.add("特别关注");
    defaultList.add("沪市");
    defaultList.add("深市");
    */
    indicator = view.findViewById(R.id.indicator_favorite);
    vp = view.findViewById(R.id.vp_favorite);
    ImageView imgGroupManager = view.findViewById(R.id.img_group_manager);
    ViewUtils.bindOnClick(this, imgGroupManager);
    callback.onRequestFavoriteGroups();
    //// TODO: 2017/12/10 debugging
//    new Handler().postDelayed(() -> imgGroupManager.performClick(), 500);
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

  @Override
  public void displayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList) {
    Log.i(TAG, "displayFavoriteGroups(): ");
    vp.setOffscreenPageLimit(favoriteGroupTypeList.size());
    vp.setAdapter(
            vpAdapter = new FavoriteAdapter(fragment.getFragmentManager())
                    .setFavoriteGroupList(favoriteGroupTypeList));
    CommonNavigator commonNavigator = new CommonNavigator(activity);
    FavoriteIndicatorAdapter indicatorAdapter =
            new FavoriteIndicatorAdapter()
                    .setFavoriteGroupTypeList(favoriteGroupTypeList)
                    .setViewPager(vp);
    commonNavigator.setPivotX(0.8f);
    commonNavigator.setAdapter(indicatorAdapter);
    indicator.setBackgroundColor(Color.TRANSPARENT);
    indicator.setNavigator(commonNavigator);
    ViewPagerHelper.bind(indicator, vp);
  }

  @Override
  public void displayFavoriteGroups() {
    vpAdapter.onQuotationListRefresh(null);
  }

  private FavoritesViewImpl() {
  }

  FavoritesViewImpl(AppCompatActivity activity) {
    this.activity = activity;
  }

  FavoritesViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {
      case R.id.img_group_manager:
        if ( !TextUtils.isEmpty(GlobalVariableTools.getAccount(activity)) )
          fragment.startActivityForResult(
                  new Intent(IntentAction.GROUP_MANAGER),
                  ActivityRequestCode.GROUP_MANAGER
          );
        break;
    }
  }
}