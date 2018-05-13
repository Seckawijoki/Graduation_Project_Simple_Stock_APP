package com.seckawijoki.graduation_project.functions.mine;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.app.FilePath;
import com.seckawijoki.graduation_project.db.client.User;
import com.seckawijoki.graduation_project.tools.FileTools;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/10 at 16:34.
 */

public class PortraitDownloadService extends Service {
  private static final String TAG = "PortraitDownloadService";
  private Handler handler;
  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    handler = new Handler();
    User user = DataSupport
            .where("userId = " + GlobalVariableTools.getUserId(getApplicationContext()))
            .findFirst(User.class);
    String fileName = user.getPortraitFileName();
    Log.d(TAG, "requestUserPortrait()\n: fileName = " + fileName);
    String savePath = FileTools.isDirectoryExistent(FilePath.USER_PORTRAIT_PATH);
    Notification.Builder builder = new Notification.Builder(getApplicationContext());

  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        // 开始执行后台任务

      }
    }).start();
    return super.onStartCommand(intent, flags, startId);
  }


}
