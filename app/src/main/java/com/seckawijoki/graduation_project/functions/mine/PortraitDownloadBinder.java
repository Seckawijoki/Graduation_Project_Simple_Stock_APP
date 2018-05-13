package com.seckawijoki.graduation_project.functions.mine;

import android.app.Service;
import android.os.Binder;

import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/10 at 16:34.
 */

public class PortraitDownloadBinder extends Binder {
  private long userId;
  private String fileName;
  private String savePath;

  PortraitDownloadBinder(long userId, String fileName, String savePath) {
    this.userId = userId;
    this.fileName = fileName;
    this.savePath = savePath;
  }

  public void startDownload(){
    new Thread(new Runnable() {
      @Override
      public void run() {
        // 执行具体的下载任务
        File portraitFile = OkHttpUtils.getFile()
                .fileName(fileName)
                .savePath(savePath)
                .url(ServerPath.GET_USER_PORTRAIT)
                .executeForFile();
      }
    }).start();
  }
}
