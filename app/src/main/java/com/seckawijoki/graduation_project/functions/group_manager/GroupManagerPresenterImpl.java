package com.seckawijoki.graduation_project.functions.group_manager;

import android.app.Activity;

import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.functions.favorite.FavoriteContract;
import com.seckawijoki.graduation_project.functions.favorite.FavoriteModelImpl;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/13 at 16:11.
 */

final class GroupManagerPresenterImpl implements GroupManagerContract.Presenter,
        GroupManagerContract.View.ActionCallback,
        GroupManagerContract.Model.DataCallback, FavoriteContract.Model.DataCallback {
  private GroupManagerContract.View view;
  private GroupManagerContract.Model model;
  private FavoriteContract.Model favoriteModel;

  @Override
  public GroupManagerContract.Presenter setView(GroupManagerContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public GroupManagerContract.Presenter setModel(GroupManagerContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public GroupManagerContract.Presenter setModel(Activity activity) {
    favoriteModel = new FavoriteModelImpl(activity);
    return this;
  }

  @Override
  public GroupManagerContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    favoriteModel.setDataCallback(this);
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
  public void onRequestAddNewGroup(String groupName) {
    model.requestAddNewGroup(groupName);
  }

  @Override
  public void onRequestFavoriteGroups() {
    favoriteModel.requestFavoriteGroups();
  }

  @Override
  public void onDisplayAddNewGroup(boolean successful, int msgId) {
view.displayAddNewGroup(successful, msgId);
  }

  @Override
  public void onDisplayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList) {
    view.displayFavoriteGroups(favoriteGroupTypeList);
  }
}