package com.seckawijoki.graduation_project.functions.information;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/31.
 */

class InformationRecyclerViewAdapter extends RecyclerView.Adapter<InformationRecyclerViewAdapter.ViewHolder> {
  private static final String TAG = "InformationRVAdapter";
  private LayoutInflater mInflater;
  private View mHeadView;
  private ViewPager mVP;
  private InformationViewPagerAdapter mVPAdapter;
  private interface ViewType{
    int HEAD = 0;
    int CONTENT = 1;
  }
  InformationRecyclerViewAdapter(Context context) {
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Log.d(TAG, "onCreateViewHolder: ");
    View view;
    if (viewType == ViewType.HEAD){
      if (mHeadView == null) {
        mHeadView = mInflater.inflate(R.layout.view_pager_information, parent, false);
      }
      if (mVP == null){
        mVP = mHeadView.findViewById(R.id.vp_information);
      }
      if (mVPAdapter == null){
        mVPAdapter = new InformationViewPagerAdapter(mInflater, null);
      }
      mVP.setAdapter(mVPAdapter);
      view = mHeadView;
    } else {
      view = mInflater.inflate(R.layout.list_item_rv_information, parent, false);
    }
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemViewType(int position) {
    return position == 0 ? ViewType.HEAD : ViewType.CONTENT;
  }

  @Override
  public int getItemCount() {
    return 10;//TODO
  }



  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView img;
    TextView tvTitle;
    TextView tvClockTime;
    private DateFormat dateFormat;
    private Random random = new Random(100000000);//TODO : to be deleted
    ViewHolder(View itemView) {
      super(itemView);
      if (itemView == mHeadView)return;
      dateFormat = new SimpleDateFormat("HH:mm");
      img = itemView.findViewById(R.id.img_rv_information);
      tvTitle = itemView.findViewById(R.id.tv_rv_information_title);
      tvClockTime = itemView.findViewById(R.id.tv_rv_information_clock_time);
    }


    void bind(int position){//TODO
      if (position == 0)return;
      img.setBackgroundColor(position * 1000);
      tvTitle.setText("Information RecyclerView Item " + position);
      tvClockTime.setText(dateFormat.format(new Date(System.currentTimeMillis() - random.nextLong())));
    }

    @Override
    public void onClick(View v) {

    }
  }
}
