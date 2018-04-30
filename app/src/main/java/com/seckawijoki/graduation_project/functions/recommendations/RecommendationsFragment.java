package com.seckawijoki.graduation_project.functions.recommendations;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
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

public class RecommendationsFragment extends Fragment {
  private RecommendationsContract.Presenter presenter;

  public static RecommendationsFragment newInstance() {
    Bundle args = new Bundle();
    RecommendationsFragment fragment = new RecommendationsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_recommendations, container, false);
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new RecommendationsPresenterImpl();
    presenter.setView(new RecommendationsViewImpl(this));
    presenter.setModel(new RecommendationsModelImpl(getActivity()));
    presenter.initiate();
  }



  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_recommendations, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }
}