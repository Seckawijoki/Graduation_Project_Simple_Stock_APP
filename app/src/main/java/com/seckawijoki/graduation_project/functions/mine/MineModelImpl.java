package com.seckawijoki.graduation_project.functions.mine;

import android.app.Activity;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.app.FilePath;
import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.client.User;
import com.seckawijoki.graduation_project.util.FileUtils;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;
import com.seckawijoki.graduation_project.util.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class MineModelImpl implements MineContract.Model {
  private static final String TAG = "MineModelImpl";
  private Activity activity;
  private DataCallback callback;
  MineModelImpl(Activity activity){
    this.activity = activity;
  }
  @Override
  public void initiate() {

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
  public void requestUserPortrait() {
    Runnable runnable = ()->{
    String userId = GlobalVariableUtils.getUserId(activity);
    String fileName = "user" + userId + "portrait.";
    Response response = OkHttpUtils.post()
            .url(ServerPath.GET_USER_PORTRAIT)
            .addParam(MoJiReTsu.USER_ID, userId)
            .executeForByteStream();
    try {
      InputStream inputStream = response.body().byteStream();
      long total = response.body().contentLength();
      String savePath = FileUtils.isDirectoryExistent(FilePath.USER_PORTRAIT_PATH);
      File portraitFile = new File(savePath, fileName);
      if (portraitFile.exists()){
        portraitFile.delete();
        Log.w(TAG, "requestUserPortrait()\n: Delete old portrait of user " + userId);
      }
      FileOutputStream fos = new FileOutputStream(portraitFile);
      byte[] buf = new byte[2048];
      long sum = 0;
      int len;
      while ( ( len = inputStream.read(buf) ) != -1 ) {
        fos.write(buf, 0, len);
        sum += len;
      }
      new User()
              .setUserId(Long.parseLong(userId))
              .setPortraitUri(portraitFile.getPath())
              .saveOrUpdate("userId = ?", userId);
      Log.d(TAG, "requestUserPortrait()\n: portraitFile = " + portraitFile);
      fos.flush();
      callback.onDisplayUserPortrait(portraitFile);
    } catch ( IOException e ) {
      e.printStackTrace();
    }
    };
    new Thread(runnable).start();
  }

  @Override
  public void requestUserInfo() {
    String userId = GlobalVariableUtils.getUserId(activity);
    JSONObject jsonObject = OkHttpUtils.post()
            .url(ServerPath.GET_USER_INFO)
            .addParam(MoJiReTsu.USER_ID, userId)
            .execute()
            .jsonObject();
    try {
      String nickname = jsonObject.getString(MoJiReTsu.NICKNAME);
              new User()
              .setNickname(nickname)
              .setPhone(jsonObject.getString(MoJiReTsu.PHONE))
                      .setUserId(Long.parseLong(userId))
                      .saveOrUpdate("userId = ?", userId);
              callback.onDisplayUserInfo(userId, nickname);
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }
}