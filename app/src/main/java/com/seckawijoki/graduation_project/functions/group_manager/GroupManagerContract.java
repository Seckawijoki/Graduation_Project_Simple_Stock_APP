package com.seckawijoki.graduation_project.functions.group_manager;

import android.app.Activity;

import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/13 at 16:11.
 */

interface GroupManagerContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayAddNewGroup(boolean successful, int msgId);
    void displayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList);
    interface ActionCallback {
      void onRequestAddNewGroup(String groupName);
      void onRequestFavoriteGroups();
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestAddNewGroup(String groupName);
    interface DataCallback {
      void onDisplayAddNewGroup(boolean successful, int msgId);
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
