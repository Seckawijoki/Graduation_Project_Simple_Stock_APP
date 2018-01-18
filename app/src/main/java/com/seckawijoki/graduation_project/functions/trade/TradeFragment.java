package com.seckawijoki.graduation_project.functions.trade;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:57.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class TradeFragment extends Fragment {
  private TradeContract.Presenter presenter;

  public static TradeFragment newInstance() {
    Bundle args = new Bundle();
    TradeFragment fragment = new TradeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_trade, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new TradePresenterImpl()
            .setModel(getActivity())
            .setView(this)
            .initiate();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    presenter.destroy();
    presenter = null;
  }

}