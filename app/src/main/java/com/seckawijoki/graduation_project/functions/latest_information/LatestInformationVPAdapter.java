package com.seckawijoki.graduation_project.functions.latest_information;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/31.
 */

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.client.SimpleInformation;

import java.util.List;

public class LatestInformationVPAdapter extends PagerAdapter {
  private static final String TAG = "LatestInfoVPAdapter";
  private View []views;
  private LayoutInflater mInflater;
  private List<SimpleInformation> informationList;
  private OnInformationClickListener listener;
  LatestInformationVPAdapter(LayoutInflater inflater, List<SimpleInformation> informationList) {
    mInflater = inflater;
    this.informationList = informationList;
    views = new View[informationList == null ? 5 : informationList.size()];
  }
  public LatestInformationVPAdapter setOnInformationClickListener(OnInformationClickListener onInformationClickListener) {
    this.listener = onInformationClickListener;
    return this;
  }

  LatestInformationVPAdapter setInformationList(List<SimpleInformation> informationList) {
    this.informationList = informationList;
    return this;
  }


  @Override
  public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
//    position = position%5;
    View view = views[position];
    if (view == null){
      view = mInflater.inflate(R.layout.view_pager_item_vp_information, container, false);
    }
    container.addView(view);
    if (informationList == null || informationList.size() <= 0){
      ( (TextView) view.findViewById(R.id.tv_vp_information) ).setText("Information ViewPager Item " + position);
      return view;
    }
    SimpleInformation information = informationList.get(position);
    if (information == null) {
      ( (TextView) view.findViewById(R.id.tv_vp_information) ).setText("Information ViewPager Item " + position);
    } else {
      ( (TextView) view.findViewById(R.id.tv_vp_information) ).setText(information.getTitle());
    }
    view.setOnClickListener(v -> listener.onInformationClick(information));
    return view;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
//    position = position%5;
    if (views[position] != null){
      container.removeView(views[position]);
    }
  }

  @Override
  public int getCount() {
    return informationList.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

}