package com.seckawijoki.graduation_project.functions.recommendations;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class RecommendationsFragment extends Fragment {
  private RecommendationsContract.Presenter presenter;
  private RecommendationsContract.View view;
  private RecommendationsContract.Model model;

  public static RecommendationsFragment getInstance(){
    return new RecommendationsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_recommendations, container, false);
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new RecommendationsPresenterImpl();
    view = new RecommendationsViewImpl(this);
    model = new RecommendationsModelImpl();
    presenter.setView(view);
    presenter.setModel(model);
    presenter.initiate();
  }

}