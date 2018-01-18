package com.seckawijoki.graduation_project.functions.favorite;

import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:32.
 */

final class FavoritePresenterImpl implements FavoriteContract.Presenter,
        FavoriteContract.View.ActionCallback,
        FavoriteContract.Model.DataCallback {
  private FavoriteContract.View view;
  private FavoriteContract.Model model;

  @Override
  public FavoriteContract.Presenter setView(FavoriteContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public FavoriteContract.Presenter setModel(FavoriteContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public FavoriteContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.onPresenterInitiate();
    model.onViewInitiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestFavoriteGroups() {
    model.requestFavoriteGroups();
  }

  @Override
  public void onDisplayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList) {
    view.displayFavoriteGroups(favoriteGroupTypeList);
  }
}