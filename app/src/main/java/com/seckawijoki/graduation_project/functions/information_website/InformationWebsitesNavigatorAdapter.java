package com.seckawijoki.graduation_project.functions.information_website;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;

import com.seckawijoki.graduation_project.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 23:40.
 */

class InformationWebsitesNavigatorAdapter extends CommonNavigatorAdapter {
  private static final String TAG = "InformationNavigatorAda";
  private String[] labels;
  private ViewPager vp;

  InformationWebsitesNavigatorAdapter(String[] labels, ViewPager vp) {
    this.labels = labels;
    this.vp = vp;
  }

  @Override
  public int getCount() {
    return labels == null ? 0 : labels.length;
  }

  @Override
  public float getTitleWeight(Context context, int index) {
    return 1;
  }

  @Override
  public IPagerTitleView getTitleView(Context context, int index) {
    ClipPagerTitleView pagerTitleView = new ClipPagerTitleView(context);
    pagerTitleView.setText(labels[index]);
    pagerTitleView.setClipColor(ContextCompat.getColor(context, R.color.tc_information_indicator_checked));
    pagerTitleView.setTextColor(ContextCompat.getColor(context, R.color.tc_information_indicator_unchecked));
    pagerTitleView.setOnClickListener(v -> vp.setCurrentItem(index));
    return pagerTitleView;
  }

  @Override
  public IPagerIndicator getIndicator(Context context) {
    LinePagerIndicator indicator = new LinePagerIndicator(context);
    float navigatorHeight = context.getResources().getDimension(R.dimen.h_information_indicator);
    float borderWidth = context.getResources().getDimension(R.dimen.stroke_information_indicator);
    float lineHeight = navigatorHeight - 2 * borderWidth;
    indicator.setLineHeight(lineHeight);
    indicator.setRoundRadius(context.getResources().getDimension(R.dimen.radius_information_indicator));
//      float xOffset = context.getResources().getDimension(R.dimen.offset_x_quotations_label);
//    indicator.setXOffset(UIUtil.dip2px(context, 4));
    indicator.setYOffset(borderWidth);
    indicator.setColors(ContextCompat.getColor(context, R.color.bg_information_indicator_checked));
    return indicator;
  }
}
