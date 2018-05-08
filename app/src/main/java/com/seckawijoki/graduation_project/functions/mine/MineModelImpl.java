package com.seckawijoki.graduation_project.functions.mine;

import android.app.Activity;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.app.FilePath;
import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.client.User;
import com.seckawijoki.graduation_project.tools.FileTools;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class MineModelImpl implements MineContract.Model {
  private static final String TAG = "MineModelImpl";
  private Activity activity;
  private DataCallback callback;

  MineModelImpl(Activity activity) {
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
    String userId = GlobalVariableTools.getUserId(activity);
    User user = DataSupport.where("userId = " + userId).findFirst(User.class);
    String fileName = user.getPortraitFileName();
    Log.d(TAG, "requestUserPortrait()\n: fileName = " + fileName);
    String savePath = FileTools.isDirectoryExistent(FilePath.USER_PORTRAIT_PATH);
    File portraitFile = OkHttpUtils.getFile()
            .fileName(fileName)
            .savePath(savePath)
            .url(ServerPath.GET_USER_PORTRAIT)
            .addParam(MoJiReTsu.PORTRAIT_FILE_NAME, user.getPortraitFileName())
            .addParam(MoJiReTsu.USER_ID, userId)
            .executeForFile();
    new User()
            .setUserId(Long.parseLong(userId))
            .setPortraitUri(portraitFile != null ? portraitFile.getPath() : null)
            .setPortraitFileName(portraitFile != null ? portraitFile.getName() : null)
            .saveOrUpdate("userId = ?", userId);
    Log.d(TAG, "requestUserPortrait()\n: portraitFile = " + portraitFile);
    callback.onDisplayUserPortrait(portraitFile);

  }

  @Override
  public void requestUserInfo() {
    String userId = GlobalVariableTools.getUserId(activity);
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
              .setPortraitFileName(jsonObject.getString(MoJiReTsu.PORTRAIT_FILE_NAME))
              .setUserId(Long.parseLong(userId))
              .saveOrUpdate("userId = ?", userId);
      callback.onDisplayUserInfo(userId, nickname);
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }
}