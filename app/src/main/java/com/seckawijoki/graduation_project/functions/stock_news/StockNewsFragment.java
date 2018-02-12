package com.seckawijoki.graduation_project.functions.stock_news;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 22:34.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class StockNewsFragment extends Fragment {
  public static StockNewsFragment newInstance() {
    Bundle args = new Bundle();
    StockNewsFragment fragment = new StockNewsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_stock_news, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

}