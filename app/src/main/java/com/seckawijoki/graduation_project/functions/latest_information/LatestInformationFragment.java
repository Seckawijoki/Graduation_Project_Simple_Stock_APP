package com.seckawijoki.graduation_project.functions.latest_information;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class LatestInformationFragment extends Fragment {
  private LatestInformationContract.Presenter presenter;
  public static LatestInformationFragment newInstance() {
    return new LatestInformationFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_latest_information, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new LatestInformationPresenterImpl()
            .setView(new LatestInformationViewImpl(this))
            .setModel(new LatestInformationModelImpl())
            .initiate();
  }


}