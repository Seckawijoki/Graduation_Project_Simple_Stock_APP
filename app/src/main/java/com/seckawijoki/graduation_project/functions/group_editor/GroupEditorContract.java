package com.seckawijoki.graduation_project.functions.group_editor;

import android.app.Activity;

import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/13 at 16:45.
 */

interface GroupEditorContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayFavoriteGroupName(List<FavoriteGroupType> favoriteGroupTypeList);
    void displayDeleteFavoriteGroups(boolean successful);
    void displayRenameFavoriteGroup(boolean successful);
    void displayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList);
    interface ActionCallback {
      void onRequestFavoriteGroups();
      void onRequestDeleteFavoriteGroups(List<String> groupNameList);
      void onRequestRenameFavoriteGroup(String oldName, String newName);
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestDeleteFavoriteGroups(List<String> groupNameList);
    void requestRenameFavoriteGroup(String oldName, String newName);
    interface DataCallback {
      void onDisplayDeleteFavoriteGroups(boolean successful);
      void onDisplayRenameFavoriteGroup(boolean successful);
      void onDisplayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList);
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
    Presenter setModel(Activity activity);
  }
}
