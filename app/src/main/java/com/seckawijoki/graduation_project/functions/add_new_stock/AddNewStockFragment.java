package com.seckawijoki.graduation_project.functions.add_new_stock;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/14 at 12:15.
 */

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public final class AddNewStockFragment extends Fragment implements AddNewStockContract.View, View.OnClickListener {
  private ActionCallback callback;
  private Activity activity;

  public static AddNewStockFragment newInstance() {
    Bundle args = new Bundle();
    AddNewStockFragment fragment = new AddNewStockFragment();
    fragment.activity = fragment.getActivity();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_add_new_stock, container, false);
    //TODO:

    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();

  }

  @Override
  public void onPresenterInitiate() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {

    }
  }

}