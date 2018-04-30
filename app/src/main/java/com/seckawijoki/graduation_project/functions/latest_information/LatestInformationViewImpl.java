package com.seckawijoki.graduation_project.functions.latest_information;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.client.Information;
import com.seckawijoki.graduation_project.db.client.SimpleInformation;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class LatestInformationViewImpl implements LatestInformationContract.View, OnInformationClickListener {
  private static final String TAG = "LatestInfoViewImpl";
  private View view;
  private Activity activity;
  private Fragment fragment;
  private ActionCallback callback;
  private LatestInformationAdapter adapter;

  @Override
  public void onModelInitiate() {
    RecyclerView rv = view.findViewById(R.id.rv_latest_information);
    adapter = new LatestInformationAdapter(activity).setOnInformationClickListener(this);
//    adapter.setInformation(getRandomInformationList());
    rv.setLayoutManager(new LinearLayoutManager(activity));
    rv.addItemDecoration(new LatestInformationDecoration(activity));
    rv.setAdapter(adapter);
    ToastUtils.show(activity, R.string.module_latest_information_under_loading);
    new Handler().postDelayed(() -> callback.onRequestLatestInformation(), 250);
  }

  private List<Information> getRandomInformationList() {
    Random random = new Random(10000000);
    List<Information> informationList = new ArrayList<>(13);
    for ( int i = 0 ; i < 13 ; i++ ) {
      Information information = new Information()
              .setTitle("title [" + i + "]: " + random.nextInt())
              .setContent(random.nextDouble() + "")
              .setDateTime(new Date(
                      System.currentTimeMillis() - random.nextLong()
              ));
      informationList.add(information);
    }
    return informationList;
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

  @Override
  public void displayLatestInformation(List<SimpleInformation> informationList) {
//    Log.d(TAG, "displayLatestInformation()\n: informationList = " + informationList);
    ToastUtils.show(activity, R.string.module_latest_information_finish_loading);
    adapter.setInformation(informationList).notifyDataSetChanged();
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

  @Override
  public void onInformationClick(SimpleInformation information) {
    if ( information == null ) {
      ToastUtils.show(activity, "Empty information.");
    } else {
      Intent intent = new Intent(IntentAction.INFORMATION_DETAILS);
      intent.putExtra(IntentKey.INFORMATION_DETAILS, information);
      activity.startActivity(intent);
    }
  }
}