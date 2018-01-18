package com.seckawijoki.graduation_project.functions.information;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class InformationViewImpl implements InformationContract.View {
  private static final String TAG = "InformationViewImpl";
  private Activity activity;
  private Fragment fragment;
  private ActionCallback callback;
  private RecyclerView rv;
  private InformationRecyclerViewAdapter rvAdapter;
  @Override
  public void initiate() {
    Log.e(TAG, "onPresenterInitiate: ");
    rv = activity.findViewById(R.id.rv_information);
    rvAdapter = new InformationRecyclerViewAdapter(activity);
    LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rv.setLayoutManager(layoutManager);
    rv.setAdapter(rvAdapter);
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

  private InformationViewImpl() {
  }

  InformationViewImpl(Activity activity) {
    this.activity = activity;
  }

  InformationViewImpl(Fragment fragment) {
    this.fragment = fragment;
    activity = fragment.getActivity();
  }
}