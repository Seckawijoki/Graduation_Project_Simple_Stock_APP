package com.seckawijoki.graduation_project.functions.group_editor;

import android.app.Activity;

import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.functions.favorite.FavoriteContract;
import com.seckawijoki.graduation_project.functions.favorite.FavoriteModelImpl;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/13 at 16:45.
 */

final class GroupEditorPresenterImpl implements GroupEditorContract.Presenter,
        GroupEditorContract.View.ActionCallback,
        GroupEditorContract.Model.DataCallback, FavoriteContract.Model.DataCallback {
  private GroupEditorContract.View view;
  private GroupEditorContract.Model model;
  private FavoriteContract.Model favoriteModel;

  @Override
  public GroupEditorContract.Presenter setView(GroupEditorContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public GroupEditorContract.Presenter setModel(GroupEditorContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public GroupEditorContract.Presenter setModel(Activity activity) {
    favoriteModel = new FavoriteModelImpl(activity);
    return this;
  }

  @Override
  public GroupEditorContract.Presenter initiate() {
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
  public void onDisplayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList) {
    view.displayFavoriteGroupName(favoriteGroupTypeList);
  }

  @Override
  public void onRequestFavoriteGroups() {
    favoriteModel.requestFavoriteGroups();
  }

  @Override
  public void onRequestDeleteFavoriteGroups(List<String> groupNameList) {
    model.requestDeleteFavoriteGroups(groupNameList);
  }

  @Override
  public void onRequestRenameFavoriteGroup(String oldName, String newName) {
    model.requestRenameFavoriteGroup(oldName, newName);
  }

  @Override
  public void onDisplayDeleteFavoriteGroups(boolean successful) {
    view.displayDeleteFavoriteGroups(successful);
  }

  @Override
  public void onDisplayRenameFavoriteGroup(boolean successful) {
    view.displayRenameFavoriteGroup(successful);
  }
}