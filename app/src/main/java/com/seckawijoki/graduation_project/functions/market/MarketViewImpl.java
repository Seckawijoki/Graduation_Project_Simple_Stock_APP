package com.seckawijoki.graduation_project.functions.market;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.client.MarketStock;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 13:41.
 */

final class MarketViewImpl implements MarketContract.View {
  private AppCompatActivity activity;
  private View view;
  private Fragment fragment;
  private ActionCallback callback;
  private MarketStockAdapter adapter;
  @Override
  public void onModelInitiate() {
    RecyclerView rv = (RecyclerView) activity.findViewById(R.id.rv_market_stock);
    GridLayoutManager layoutManager = new GridLayoutManager(activity, 3);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rv.setLayoutManager(layoutManager);
//    rv.setLayoutManager(new LinearLayoutManager(activity));
    adapter = new MarketStockAdapter(activity);
    rv.setAdapter(adapter);
    callback.onRequestMarketStocks();
  }

  @Override
  public void destroy() {
    activity = null;
    fragment = null;
    callback = null;
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayMarketStocks(List<MarketStock> marketStockList) {
    if (adapter == null){
      adapter = new MarketStockAdapter(activity);
    }
    adapter.setMarketStockList(marketStockList).notifyDataSetChanged();
    ( (RecyclerView) view.findViewById(R.id.rv_market_stock) ).setAdapter(adapter);
  }

  private MarketViewImpl() {
  }

  MarketViewImpl(AppCompatActivity activity) {
    this.activity = activity;
  }

  MarketViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }
}