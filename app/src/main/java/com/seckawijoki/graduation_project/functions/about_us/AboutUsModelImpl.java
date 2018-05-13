package com.seckawijoki.graduation_project.functions.about_us;

import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 17:54.
 */

final class AboutUsModelImpl implements AboutUsContract.Model {
  private DataCallback callback;
  private int latestVersionCode;
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
  public void requestLatestAppVersionCode() {
    JSONObject jsonObject = OkHttpUtils.get()
            .url(ServerPath.GET_LATEST_APP_VERSION_CODE)
            .execute()
            .jsonObject();
    try {
      callback.onDisplayLatestAppVersionCode(
              jsonObject.getInt(MoJiReTsu.VERSION_CODE) + 1
      );
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }

  @Override
  public void requestLatestAppVersion() {
    JSONObject jsonObject = OkHttpUtils.get()
            .url(ServerPath.GET_LATEST_APP_VERSION)
            .execute()
            .jsonObject();
    try {
      callback.onDisplayLatestAppVersion(
              jsonObject.getInt(MoJiReTsu.VERSION_CODE),
              jsonObject.getString(MoJiReTsu.VERSION_NAME),
              jsonObject.getString(MoJiReTsu.VERSION_DESCRIPTION));
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }

  @Override
  public void requestLatestApk() {

  }
}