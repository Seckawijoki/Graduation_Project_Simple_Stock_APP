package com.seckawijoki.graduation_project.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 20:10.
 */

public class FileUtils {
  private FileUtils(){}
  public static File getTempImage() {
    if (android.os.Environment.getExternalStorageState().equals(
            android.os.Environment.MEDIA_MOUNTED)) {
      File tempFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
      try {
        tempFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }

      return tempFile;
    }
    return null;
  }
  public static boolean isSDCardExistent() {
    return Environment.getExternalStorageState().equals(
            Environment.MEDIA_MOUNTED);
  }
  /**
   * @param saveDir
   * @return
   * @throws IOException 判断下载目录是否存在
   */
  public static String isDirectoryExistent(String saveDir) throws IOException {
    // 下载位置
    File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
//    File downloadFile = new File(saveDir);
    if ( !downloadFile.mkdirs() ) {
      downloadFile.createNewFile();
    }
    String savePath = downloadFile.getAbsolutePath();
    return savePath;
  }
}
