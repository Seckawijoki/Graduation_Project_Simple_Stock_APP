package com.seckawijoki.graduation_project.functions.favorite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityIntent;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;
import com.seckawijoki.graduation_project.util.ViewUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

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
//    if ( vpAdapter == null )
    vp.setOffscreenPageLimit(favoriteGroupTypeList.size());
    vp.setAdapter(
            vpAdapter = new FavoriteAdapter(fragment.getFragmentManager())
                    .setFavoriteGroupList(favoriteGroupTypeList));
    CommonNavigator commonNavigator = new CommonNavigator(activity);
    FavoriteIndicatorAdapter indicatorAdapter =
            new FavoriteIndicatorAdapter()
                    .setFavoriteGroupTypeList(favoriteGroupTypeList);
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
        if ( !TextUtils.isEmpty(GlobalVariableUtils.getAccount(activity)) )
          fragment.startActivityForResult(
                  new Intent(ActivityIntent.GROUP_MANAGER),
                  ActivityRequestCode.ADD_NEW_GROUP);
        break;
    }
  }

  private class FavoriteIndicatorAdapter extends CommonNavigatorAdapter {
    private List<FavoriteGroupType> favoriteGroupTypeList;

    public FavoriteIndicatorAdapter setFavoriteGroupTypeList(List<FavoriteGroupType> favoriteGroupTypeList) {
      this.favoriteGroupTypeList = favoriteGroupTypeList;
      return this;
    }

    @Override
    public int getCount() {
      return favoriteGroupTypeList == null ? 0 : favoriteGroupTypeList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
      SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
      simplePagerTitleView.setNormalColor(
              ContextCompat.getColor(context, R.color.bg_favorites_label_dark));
      simplePagerTitleView.setSelectedColor(
              ContextCompat.getColor(context, R.color.bg_favorites_label_light));
      simplePagerTitleView.setPadding(16, 0, 16, 0);
      simplePagerTitleView.setText(favoriteGroupTypeList.get(index).getFavoriteGroupName());
      simplePagerTitleView.setOnClickListener(v -> vp.setCurrentItem(index));
      return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
      LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
      linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
      float width = context.getResources().getDimension(R.dimen.min_w_favorite_group_label);
      linePagerIndicator.setColors(ContextCompat.getColor(context, R.color.bg_favorites_label_light));
      linePagerIndicator.setMinimumWidth(UIUtil.dip2px(context, width));
      return linePagerIndicator;
    }
  }

  ;
}