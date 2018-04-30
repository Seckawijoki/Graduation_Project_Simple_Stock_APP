package com.seckawijoki.graduation_project.functions.latest_information;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.client.SimpleInformation;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/31.
 */

class LatestInformationAdapter extends RecyclerView.Adapter<LatestInformationAdapter.ViewHolder> {
  private static final String TAG = "LatestInfoAdapter";
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");
  private Context context;
  private View mHeadView;
  private List<SimpleInformation> topInformationList;
  private List<SimpleInformation> otherInformationList;
  private LatestInformationVPAdapter mVPAdapter;
  private OnInformationClickListener listener;
  private int viewPagerCount = 5;

  LatestInformationAdapter setOnInformationClickListener(OnInformationClickListener onInformationClickListener) {
    this.listener = onInformationClickListener;
    return this;
  }

  LatestInformationAdapter setInformation(List<SimpleInformation> informationList) {
    if ( informationList == null || informationList.size() <= 0 ) {
      topInformationList = null;
      otherInformationList = null;
      return this;
    } else {
      if ( informationList.size() <= 5 )
        viewPagerCount = 1;
      topInformationList = informationList.subList(0, viewPagerCount);
      if ( informationList.size() > 1 )
        otherInformationList = informationList.subList(viewPagerCount, informationList.size() - 1);
    }
    return this;
  }

  private interface LatestType {
    int HEAD = 0;
    int CONTENT = 1;
  }

  LatestInformationAdapter(Context context) {
    this.context = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    if ( viewType == LatestType.HEAD ) {
      if ( mHeadView == null ) {
        mHeadView = LayoutInflater.from(context).inflate(R.layout.view_pager_top_latest_information, parent, false);
      }
      view = mHeadView;
    } else {
      view = LayoutInflater.from(context).inflate(R.layout.recycler_item_rv_latest_information, parent, false);
    }
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if ( position == LatestType.HEAD ) {
      ViewPager vp = mHeadView.findViewById(R.id.vp_latest_information);
      MagicIndicator mi = mHeadView.findViewById(R.id.indicator_latest_information);
      CircleNavigator circleNavigator = new CircleNavigator(context);
      circleNavigator.setCircleCount(viewPagerCount);
      circleNavigator.setCircleColor(Color.BLACK);
      circleNavigator.setFollowTouch(true);
      mi.setNavigator(circleNavigator);
      ViewPagerHelper.bind(mi, vp);
      if ( mVPAdapter == null ) {
        mVPAdapter = new LatestInformationVPAdapter(LayoutInflater.from(context), null);
      }
      mVPAdapter.setOnInformationClickListener(listener)
              .setInformationList(topInformationList)
              .notifyDataSetChanged();
      vp.setAdapter(mVPAdapter);
    } else {
      if ( otherInformationList == null || otherInformationList.size() <= 0 ) {
        holder.bind(position + 5);
        return;
      }
      SimpleInformation information = otherInformationList.get(position - 1);
      if ( information == null ) {
        holder.bind(position + 5);
      } else {
        holder.tvTitle.setText(information.getTitle());
        holder.tvClockTime.setText(new PrettyTime().format(information.getDateTime()));
//        holder.tvClockTime.setText(DATE_FORMAT.format(information.getDateTime()));
      }
      holder.layout.setOnClickListener(v -> listener.onInformationClick(information));
    }
  }

  @Override
  public int getItemViewType(int position) {
    return position == 0 ? LatestType.HEAD : LatestType.CONTENT;
  }

  @Override
  public int getItemCount() {
    if ( topInformationList == null ) return 0;
    else if ( otherInformationList == null ) return 1;
    else return otherInformationList.size() + 1;
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View layout;
    ImageView img;
    TextView tvTitle;
    TextView tvClockTime;
    private Random random = new Random(100000000);//TODO : to be deleted

    ViewHolder(View itemView) {
      super(itemView);
      if ( itemView == mHeadView ) return;
      layout = itemView.findViewById(R.id.layout_list_item_rv_latest_information);
      img = itemView.findViewById(R.id.img_rv_latest_information);
//      img.setVisibility(View.GONE);
      tvTitle = itemView.findViewById(R.id.tv_rv_latest_information_title);
      tvClockTime = itemView.findViewById(R.id.tv_rv_latest_information_clock_time);
    }


    void bind(int position) {//TODO
      if ( position == 0 ) return;
      img.setBackgroundColor(position * 1000);
      tvTitle.setText("Information RecyclerView Item " + position);
      tvClockTime.setText(DATE_FORMAT.format(
              new Date(System.currentTimeMillis() - random.nextLong())
      ));
    }

    @Override
    public void onClick(View v) {

    }
  }
}
