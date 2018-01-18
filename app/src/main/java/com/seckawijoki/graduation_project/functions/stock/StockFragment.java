package com.seckawijoki.graduation_project.functions.stock;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:45.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class StockFragment extends Fragment {
  private StockContract.Presenter presenter;
  private StockContract.View view;
  private StockContract.Model model;

  public static StockFragment newInstance() {
    Bundle args = new Bundle();
    StockFragment fragment = new StockFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_stock, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new StockPresenterImpl();
    view = new StockViewImpl(this);
    model = new StockModelImpl();
    presenter.setView(view);
    presenter.setModel(model);
    presenter.initiate();
  }

}