package com.seckawijoki.graduation_project.functions.information;
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

public class InformationFragment extends Fragment {
  private static final String TAG = "InformationFragment";
  private InformationContract.Presenter presenter;
  private InformationContract.View view;
  private InformationContract.Model model;
  public static InformationFragment getInstance() {
    Log.e(TAG, "getInstance: ");
    return new InformationFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_information, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    Log.e(TAG, "onActivityCreated: ");
    super.onActivityCreated(savedInstanceState);
    presenter = new InformationPresenterImpl();
    view = new InformationViewImpl(this);
    model = new InformationModelImpl();
    presenter.setView(view);
    presenter.setModel(model);
    presenter.initiate();
  }


}