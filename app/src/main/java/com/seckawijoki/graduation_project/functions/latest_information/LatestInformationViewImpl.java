package com.seckawijoki.graduation_project.functions.latest_information;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class LatestInformationViewImpl implements LatestInformationContract.View {
  private static final String TAG = "LatestInfoViewImpl";
  private View view;
  private Activity activity;
  private Fragment fragment;
  private ActionCallback callback;
  private LatestInformationAdapter adapter;
  @Override
  public void onModelInitiate() {
    RecyclerView rv = view.findViewById(R.id.rv_latest_information);
    adapter = new LatestInformationAdapter(activity);
    rv.setLayoutManager(new LinearLayoutManager(activity));
    rv.addItemDecoration(new LatestInformationDecoration(activity));
    rv.setAdapter(adapter);
  }

  @Override
  public void destroy() {
    fragment = null;
    activity = null;
    callback = null;
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  private LatestInformationViewImpl() {
  }

  LatestInformationViewImpl(Activity activity) {
    this.activity = activity;
  }

  LatestInformationViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = fragment.getActivity();
  }
}