package com.seckawijoki.graduation_project.functions.group_editor;

import android.app.Activity;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;

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
 * Created by 瑶琴频曲羽衣魂 on 2017/12/13 at 16:45.
 */

final class GroupEditorModelImpl implements GroupEditorContract.Model {
  private static final String TAG = "GroupEditorModelImpl";
  private DataCallback callback;
  private ExecutorService pool = Executors.newSingleThreadExecutor();
  private Activity activity;

  GroupEditorModelImpl(Activity activity) {
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
  public void requestDeleteFavoriteGroups(List<String> groupNameList) {
    Log.i(TAG, "requestDeleteFavoriteGroups(): ");
    Callable<String> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      FormBody.Builder formBodyBuilder = new FormBody.Builder()
              .add("userId", GlobalVariableTools.getUserId(activity));
      for ( int i = 0 ; i < groupNameList.size() ; i++ ) {
        String groupName = groupNameList.get(i);
        FavoriteGroupType favoriteGroupType =
                DataSupport.where("favoriteGroupName = ?", groupName)
                .select("favoriteGroupId")
                .findFirst(FavoriteGroupType.class);
        formBodyBuilder.add("favoriteGroupId", favoriteGroupType.getFavoriteGroupId() + "");
      }
      RequestBody requestBody = formBodyBuilder.build();
      Request request = new Request.Builder()
              .url(ServerPath.DELETE_FAVORITE_GROUPS)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      if (!response.isSuccessful())
        return null;
      String result = response.body().string();
      return result;
    };
    Future<String> future = pool.submit(callable);
    try {
      String result = future.get();
      DataSupport.deleteAll(FavoriteGroupType.class);
      JSONArray jsonArray = new JSONArray(result);
      for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        new FavoriteGroupType()
                .setFavoriteGroupId(jsonObject.getLong("favoriteGroupId"))
                .setFavoriteGroupName(jsonObject.getString("favoriteGroupName"))
                .setStockCount(jsonObject.getInt("stockCount"))
                .setRankWeight(jsonObject.getInt("rankWeight"))
                .saveOrUpdate("favoriteGroupId = ?", jsonObject.getString("favoriteGroupId"));
      }
      List<FavoriteGroupType> favoriteGroupTypeList =
              DataSupport.order("rankWeight desc")
              .find(FavoriteGroupType.class);
      callback.onDisplayFavoriteGroups(favoriteGroupTypeList);
      callback.onDisplayDeleteFavoriteGroups(true);
    } catch ( InterruptedException e ) {
      e.printStackTrace();
    } catch ( ExecutionException e ) {
      e.printStackTrace();
    } catch ( JSONException e ) {
      e.printStackTrace();
      callback.onDisplayDeleteFavoriteGroups(false);
    }
  }

  @Override
  public void requestRenameFavoriteGroup(String oldGroupName, String newGroupName){
    Callable<String> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("userId", GlobalVariableTools.getUserId(activity))
              .add("oldGroupName", oldGroupName)
              .add("newGroupName", newGroupName)
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.RENAME_FAVORITE_GROUP)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      if (!response.isSuccessful())
        return null;
      String result = response.body().string();
      return result;
    };
    Future<String> future = pool.submit(callable);
    try {
      String result = future.get();
      JSONObject jsonObject = new JSONObject(result);
      new FavoriteGroupType()
              .setFavoriteGroupId(jsonObject.getLong("favoriteGroupId"))
              .setFavoriteGroupName(newGroupName)
              .setRankWeight(jsonObject.getInt("rankWeight"))
              .saveOrUpdate("favoriteGroupId = ?", jsonObject.getString("favoriteGroupId"));
      List<FavoriteGroupType> favoriteGroupTypeList =
              DataSupport.order("rankWeight desc")
              .find(FavoriteGroupType.class);
      callback.onDisplayFavoriteGroups(favoriteGroupTypeList);
      callback.onDisplayRenameFavoriteGroup(true);
    } catch ( InterruptedException | ExecutionException | JSONException e ) {
      e.printStackTrace();
      callback.onDisplayRenameFavoriteGroup(false);
    }
  }
}