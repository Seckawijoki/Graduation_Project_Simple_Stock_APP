package com.seckawijoki.graduation_project.functions.full_screen_image;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 13:21.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class FullScreenImageFragment extends Fragment {
  public static FullScreenImageFragment newInstance() {
    Bundle args = new Bundle();
    FullScreenImageFragment fragment = new FullScreenImageFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_full_screen_image, container, false);
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