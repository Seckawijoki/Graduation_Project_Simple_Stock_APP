package com.seckawijoki.graduation_project.functions.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 9:26.
 */

public class MarketFragment extends Fragment{

  public static MarketFragment newInstance() {
    Bundle args = new Bundle();
    MarketFragment fragment = new MarketFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_market, container, false);
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    new MarketPresenterImpl()
            .setView(new MarketViewImpl(this))
            .setModel(new MarketModelImpl())
            .initiate();
  }
}
