package com.seckawijoki.graduation_project.functions.mine;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;

public class MineFragment extends Fragment {
  private MineContract.Presenter presenter;
  private MineContract.View view;
  private MineContract.Model model;

  public static MineFragment getInstance(){
    return new MineFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_mine, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new MinePresenterImpl();
    view = new MineViewImpl(this);
    model = new MineModelImpl();
    presenter.setView(view);
    presenter.setModel(model);
    presenter.initiate();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null)return;
    super.onActivityResult(requestCode, resultCode, data);
    switch ( resultCode ){
      case Activity.RESULT_OK:
        switch ( requestCode ){
          case ActivityRequestCode.LOGOUT:
            boolean hasLoggedOut = data.getBooleanExtra(IntentKey.HAS_LOGGED_OUT, false);
            view.displayUserInformation(!hasLoggedOut);
            break;
        }
        break;
      case Activity.RESULT_CANCELED:
      case Activity.RESULT_FIRST_USER:

        break;
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_mine, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }
}