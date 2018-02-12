package com.seckawijoki.graduation_project.functions.favorite;

import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:30.
 */

public interface FavoriteContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList);
    void displayFavoriteGroups();
    interface ActionCallback {
      void onRequestFavoriteGroups();
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestFavoriteGroups();
    void requestFavoriteGroupsFromDatabase();
    interface DataCallback {
      void onDisplayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList);
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
