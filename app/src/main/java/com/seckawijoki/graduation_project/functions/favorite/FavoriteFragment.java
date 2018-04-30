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
import com.seckawijoki.graduation_project.constants.common.IntentKey;

public class FavoriteFragment extends Fragment {
  private static final String TAG = "FavoriteFragment";
  private FavoriteContract.Presenter presenter;
  private FavoriteContract.View view;
  private FavoriteContract.Model model;

  public FavoriteContract.View getMvpView() {
    return view;
  }

  public long getFavoriteGroupId(){
    return ( (FavoritesViewImpl) view ).getFavoriteGroupId();
  }
  public void refreshQuotationLists(){
    ( (FavoritesViewImpl) view ).getFavoriteAdapter().onQuotationListRefresh(null);
  }

  public FavoriteContract.Model getMvpModel() {
    return model;
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
    super.onActivityResult(requestCode, resultCode, data);
    Log.i(TAG, "onActivityResult()\n: ");
    if ( resultCode == Activity.RESULT_OK && requestCode == ActivityRequestCode.GROUP_MANAGER ) {
      boolean hasDeleted = data.getBooleanExtra(IntentKey.HAS_DELETED_GROUP, false);
      boolean hasRenamed = data.getBooleanExtra(IntentKey.HAS_RENAMED_GROUP, false);
      boolean hasAdded = data.getBooleanExtra(IntentKey.HAS_ADDED_NEW_GROUP, false);
      Log.d(TAG, "onActivityResult():\n hasAdded = " + hasAdded);
      Log.d(TAG, "onActivityResult():\n hasDeleted = " + hasDeleted);
      Log.d(TAG, "onActivityResult():\n hasRenamed = " + hasRenamed);
      if ( hasDeleted || hasRenamed || hasAdded ) {
        Log.i(TAG, "onActivityResult()\n: ");
        model.requestFavoriteGroups();
      }
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

}