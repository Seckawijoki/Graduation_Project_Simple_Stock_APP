package com.seckawijoki.graduation_project.functions.group_manager;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by 瑶琴频曲羽衣魂 on 2017/12/13 at 16:11.
 */

final class GroupManagerModelImpl implements GroupManagerContract.Model {
  private static final String TAG = "GroupManagerModelImpl";
  private ExecutorService pool = Executors.newFixedThreadPool(2);
  private DataCallback callback;
  private Activity activity;

  GroupManagerModelImpl(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onViewInitiate() {

  }

  @Override
  public void destroy() {
    callback = null;
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestAddNewGroup(String favoriteGroupName) {
    if ( TextUtils.isEmpty(favoriteGroupName)){
      callback.onDisplayAddNewGroup(false, R.string.error_group_name_empty);
      return;
    }
    if (favoriteGroupName.length() > 5){
      callback.onDisplayAddNewGroup(false, R.string.error_group_name_too_long);
      return;
    }
    Callable<String> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("userId", GlobalVariableUtils.getUserId(activity))
              .add("favoriteGroupName", favoriteGroupName)
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.ADD_FAVORITE_GROUP)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      if (!response.isSuccessful())return null;
      String result = response.body().string();
      Log.d(TAG, "call(): result = " + result);
      return result;
    };
    Future<String> future = pool.submit(callable);
    try {
      String result = future.get();
      JSONObject jsonObject = new JSONObject(result);
        new FavoriteGroupType()
                .setStockCount(0)
                .setFavoriteGroupId(jsonObject.getLong("favoriteGroupId"))
                .setFavoriteGroupName(favoriteGroupName)
                .setRankWeight(jsonObject.getInt("rankWeight"))
                .saveOrUpdate("favoriteGroupId = ?", jsonObject.getString("favoriteGroupId"));
        callback.onDisplayAddNewGroup(true, R.string.msg_succeed_in_adding_new_group);
    } catch ( InterruptedException | ExecutionException e ) {
      e.printStackTrace();
    } catch ( JSONException e ) {
      callback.onDisplayAddNewGroup(false, R.string.error_the_group_has_existed);
      e.printStackTrace();
    }
  }
}