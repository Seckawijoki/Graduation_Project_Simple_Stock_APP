package com.seckawijoki.graduation_project.functions.quotations_old;
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

public class OldQuotationsFragment extends Fragment {
  private QuotationsContract.Presenter presenter;
  private QuotationsContract.View view;
  private QuotationsContract.Model model;
  public static OldQuotationsFragment newInstance() {
    Bundle args = new Bundle();
    OldQuotationsFragment fragment = new OldQuotationsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_quotations_old, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new QuotationsPresenterImpl();
    view = new QuotationsViewImpl(this);
    model = new QuotationsModelImpl();
    presenter.setView(view);
    presenter.setModel(model);
    presenter.initiate();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    presenter.destroy();
  }
}