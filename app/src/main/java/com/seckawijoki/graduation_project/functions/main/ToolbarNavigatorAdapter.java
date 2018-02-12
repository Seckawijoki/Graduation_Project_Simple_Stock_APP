package com.seckawijoki.graduation_project.functions.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

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

public class ToolbarNavigatorAdapter extends CommonNavigatorAdapter {
  private String[] labels;
  private ViewPager vp;

  public ToolbarNavigatorAdapter(String[] labels, ViewPager vp) {
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
    pagerTitleView.setClipColor(ContextCompat.getColor(context, R.color.tc_default_magic_indicator_label_checked));
    pagerTitleView.setTextColor(ContextCompat.getColor(context, R.color.tc_default_magic_indicator_label));
    pagerTitleView.setOnClickListener(v -> vp.setCurrentItem(index));
    return pagerTitleView;
  }

  @Override
  public IPagerIndicator getIndicator(Context context) {
    LinePagerIndicator indicator = new LinePagerIndicator(context);
    float navigatorHeight = context.getResources().getDimension(R.dimen.h_common_navigator);
    float borderWidth = UIUtil.dip2px(context, 1);
    float lineHeight = navigatorHeight - 2 * borderWidth;
    float lineWidth = context.getResources().getDimension(R.dimen.w_quotations_magic_indicator);
    indicator.setLineWidth(lineWidth);
    indicator.setLineHeight(lineHeight);
//      float xOffset = context.getResources().getDimension(R.dimen.offset_x_quotations_label);
//      indicator.setXOffset(UIUtil.dip2px(context, xOffset));
    indicator.setYOffset(borderWidth);
    indicator.setColors(ContextCompat.getColor(context, R.color.bg_default_magic_indicator));
    return indicator;
  }
}
