package com.seckawijoki.graduation_project.functions.information;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/8 at 17:25.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class InformationFragment extends Fragment {
  public static InformationFragment newInstance() {
    Bundle args = new Bundle();
    InformationFragment fragment = new InformationFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_information, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    new InformationPresenterImpl()
            .setModel(getActivity())
            .setView(this)
            .initiate();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_information, menu);
  }
}