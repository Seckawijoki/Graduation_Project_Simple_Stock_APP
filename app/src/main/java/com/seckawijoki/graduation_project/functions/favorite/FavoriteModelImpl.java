package com.seckawijoki.graduation_project.functions.favorite;

import android.app.Activity;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;
import com.seckawijoki.graduation_project.util.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:30.
 */

public final class FavoriteModelImpl implements FavoriteContract.Model {
  private static final String TAG = "FavoriteModelImpl";
  private DataCallback callback;
  private Activity activity;
  private ExecutorService pool = Executors.newFixedThreadPool(1);

  public FavoriteModelImpl(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onViewInitiate() {

  }

  @Override
  public void destroy() {
    callback = null;
    pool.shutdownNow();
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestFavoriteGroups() {
    JSONArray jsonArray = OkHttpUtils.post()
            .url(ServerPath.GET_FAVORITE_GROUPS)
            .addParam("userId", GlobalVariableUtils.getUserId(activity))
            .execute()
            .jsonArray();
    try {
      DataSupport.deleteAll(FavoriteGroupType.class);
      for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Log.d(TAG, "requestFavoriteGroups()\n: jsonObject = " + jsonObject);
        new FavoriteGroupType()
                .setFavoriteGroupId(jsonObject.getLong("favoriteGroupId"))
                .setRankWeight(jsonObject.getInt("rankWeight"))
                .setFavoriteGroupName(jsonObject.getString("favoriteGroupName"))
                .setStockCount(jsonObject.getInt("stockCount"))
                .saveOrUpdate("favoriteGroupId = ?", jsonObject.getString("favoriteGroupId"));
      }
      List<FavoriteGroupType> dbList =
              DataSupport
                      .order("rankWeight desc")
                      .find(FavoriteGroupType.class);
      Log.d(TAG, "requestFavoriteGroups(): dbList = " + dbList);
      callback.onDisplayFavoriteGroups(dbList);
    } catch ( JSONException e ) {
      List<FavoriteGroupType> dbList =
              DataSupport
                      .order("rankWeight desc")
                      .find(FavoriteGroupType.class);
      Log.e(TAG, "requestFavoriteGroups(): dbList = " + dbList);
      callback.onDisplayFavoriteGroups(dbList);
    }
//      return dbList;
//    };

  }

}