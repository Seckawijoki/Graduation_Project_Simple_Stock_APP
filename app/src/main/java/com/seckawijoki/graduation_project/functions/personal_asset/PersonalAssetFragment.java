package com.seckawijoki.graduation_project.functions.personal_asset;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 20:02.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class PersonalAssetFragment extends Fragment {
  private PersonalAssetContract.Presenter presenter;

  public static PersonalAssetFragment newInstance() {
    Bundle args = new Bundle();
    PersonalAssetFragment fragment = new PersonalAssetFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_personal_asset, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new PersonalAssetPresenterImpl()
            .setModel(getActivity())
            .setView(this)
            .initiate();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    presenter.destroy();
    presenter = null;
  }

}