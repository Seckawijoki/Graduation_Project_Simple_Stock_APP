package com.seckawijoki.graduation_project.functions.favorite;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:32.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;

public class FavoriteFragment extends Fragment {
  private static final String TAG = "FavoriteFragment";
  private FavoriteContract.Presenter presenter;
  private FavoriteContract.View view;
  private FavoriteContract.Model model;

  public FavoriteContract.View getMvpView() {
    return view;
  }

  public static FavoriteFragment newInstance() {
    Bundle args = new Bundle();
    FavoriteFragment fragment = new FavoriteFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_favorite, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new FavoritePresenterImpl()
            .setView(view = new FavoritesViewImpl(this))
            .setModel(model = new FavoriteModelImpl(getActivity()))
            .initiate();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.i(TAG, "onActivityResult(): ");
    switch ( resultCode ){
      case Activity.RESULT_OK:
        if (requestCode == ActivityRequestCode.ADD_NEW_GROUP) {
          /*
          presenter = new FavoritePresenterImpl()
                  .setView(view = new FavoritesViewImpl(this))
                  .setModel(model = new FavoriteModelImpl())
                  .initiate();
          */
          model.requestFavoriteGroups();
        }
        break;
      default:

        break;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    presenter.destroy();
    view = null;
    model = null;
    presenter = null;
  }

}