package com.seckawijoki.graduation_project.functions.personal_info;

import android.app.Activity;
import android.util.Log;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.FilePath;
import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.client.User;
import com.seckawijoki.graduation_project.tools.FileTools;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/21 at 19:36.
 */

final class PersonalInfoModelImpl implements PersonalInfoContract.Model {
  private static final String TAG = "PersonalInfoModelImpl";
  private DataCallback callback;
  private Activity activity;
  private ExecutorService singleThread = Executors.newSingleThreadExecutor();
  @Override
  public void onViewInitiate() {

  }
  PersonalInfoModelImpl(Activity activity){
    this.activity = activity;
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
  public void requestUploadPortrait(String filePath) {
    String userId = GlobalVariableTools
            .getUserId(activity);
    File file = new File(filePath);
    String fileName = FileTools.getPortraitFileName(userId);
    String postfix = filePath.substring(filePath.lastIndexOf(".")+1);
    String result = OkHttpUtils.postFile()
            .create("image/*", file)
            .url(ServerPath.UPLOAD_USER_PORTRAIT)
            .addParam(MoJiReTsu.USER_ID, GlobalVariableTools.getUserId(activity))
            .addFormDataPart(fileName,  fileName + "." + postfix)
            .execute()
            .string();
    try {
      Log.d(TAG, "requestUploadPortrait()\n: result = " + result);
      JSONObject jsonObject = new JSONObject(result);
      boolean successful = jsonObject.getBoolean(MoJiReTsu.RESULT);
      if (successful){
        new User()
                .setUserId(Long.parseLong(userId))
                .setPortraitUri(filePath)
                .saveOrUpdate("userId = ?", userId);
        callback.onDisplayUpdatePersonalInfo();
        ToastUtils.show(activity, R.string.msg_succeed_in_uploading_portrait);
      } else {
        ToastUtils.show(activity, R.string.error_failed_to_change_user_portrait);
      }
      Log.d(TAG, "requestUploadPortrait()\n: result = " + result);
    } catch ( JSONException e ) {
      e.printStackTrace();
    }
  }

  @Override
  public void requestChangeEmail(String email) {

  }

  @Override
  public void requestChangeNickname(String nickname) {
    String userId = GlobalVariableTools.getUserId(activity);
    boolean result = OkHttpUtils.post()
            .url(ServerPath.CHANGE_NICKNAME)
            .addParam(MoJiReTsu.USER_ID, userId)
            .addParam(MoJiReTsu.NICKNAME, nickname)
            .execute()
            .bool();
    if (result){
      new User()
              .setUserId(userId)
              .setNickname(nickname)
              .saveOrUpdate("userId = ?", userId);
      callback.onDisplayUpdatePersonalInfo();
      ToastUtils.show(activity, R.string.msg_succeed_in_changing_nickname);
    } else {
      ToastUtils.show(activity, R.string.error_failed_to_change_nickname);
    }
  }
}