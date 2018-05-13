package com.seckawijoki.graduation_project.functions.disclaimer;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/13 at 14:29.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class DisclaimerFragment extends Fragment {
  public static DisclaimerFragment newInstance() {
    Bundle args = new Bundle();
    DisclaimerFragment fragment = new DisclaimerFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_disclaimer, container, false);
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