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
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_mine, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new MinePresenterImpl();
    view = new MineViewImpl(this);
    model = new MineModelImpl(getActivity());
    presenter.setView(view);
    presenter.setModel(model);
    presenter.initiate();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data == null)return;
    if ( resultCode == Activity.RESULT_OK ) {
      if ( requestCode == ActivityRequestCode.SETTINGS ) {
        boolean hasLoggedOut = data.getBooleanExtra(IntentKey.HAS_LOGGED_OUT, false);
        view.displayShowUserInfo(!hasLoggedOut);

      } else if ( requestCode == ActivityRequestCode.PERSONAL_INFO ) {
        model.requestUserInfo();
        model.requestUserPortrait();
      }
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_mine, menu);
  }
}