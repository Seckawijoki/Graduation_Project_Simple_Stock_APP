package com.seckawijoki.graduation_project.functions.favorite;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/2 at 18:50.
 */
class FavoriteIndicatorAdapter extends CommonNavigatorAdapter {
  private List<FavoriteGroupType> favoriteGroupTypeList;
  private ViewPager vp;
  FavoriteIndicatorAdapter setViewPager(ViewPager viewPager) {
    this.vp = viewPager;
    return this;
  }

  FavoriteIndicatorAdapter setFavoriteGroupTypeList(List<FavoriteGroupType> favoriteGroupTypeList) {
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
