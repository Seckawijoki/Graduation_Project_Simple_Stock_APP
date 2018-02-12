package com.seckawijoki.graduation_project.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/12 at 11:46.
 */

public class DownloadUtils implements Callback {
  private static final String TAG = "DownloadUtils";
  private static DownloadUtils instance;
  private final OkHttpClient okHttpClient;
  private String saveDir;
  private String url;
  private String fileName;
  private OnDownloadListener listener;
  public static DownloadUtils get() {
    if ( instance == null ) {
      instance = new DownloadUtils();
    }
    return instance;
  }

  private DownloadUtils() {
    okHttpClient = new OkHttpClient();
  }

  @Override
  public void onFailure(Call call, IOException e) {
    // 下载失败
    listener.onDownloadFailed();
  }

  @Override
  public void onResponse(Call call, Response response) throws IOException {
    InputStream is = null;
    byte[] buf = new byte[2048];
    int len;
    FileOutputStream fos = null;
    // 储存下载文件的目录
    String savePath = isExistDir(saveDir);
    try {
      is = response.body().byteStream();
      long total = response.body().contentLength();
      if ( TextUtils.isEmpty(fileName) ) {
        fileName = getNameFromUrl(url);
      }
      File file = new File(savePath, fileName);
      fos = new FileOutputStream(file);
      long sum = 0;
      while ( ( len = is.read(buf) ) != -1 ) {
        fos.write(buf, 0, len);
        sum += len;
        double progress = sum * 1.0 / total * 100 ;
        // 下载中
        listener.onDownloading(progress);
      }
      fos.flush();
      // 下载完成
      fileName = null;
      listener.onDownloadSuccess(file);
      Log.i(TAG, "onResponse()\n: Finished.");
    } catch ( Exception e ) {
      listener.onDownloadFailed();
    } finally {
      try {
        if ( is != null )
          is.close();
      } catch ( IOException ignored ) {
      }
      try {
        if ( fos != null )
          fos.close();
      } catch ( IOException ignored ) {
      }
    }
  }

  public void download(String url, String saveDir, String fileName, OnDownloadListener listener) {
    this.url = url;
    this.fileName = fileName;
    this.saveDir = saveDir;
    this.listener = listener;
    Request request = new Request.Builder().url(url).build();
    okHttpClient.newCall(request).enqueue(this);
  }

  /**
   * @param url      下载连接
   * @param saveDir  储存下载文件的SDCard目录
   * @param listener 下载监听
   */
  public void download(String url, String saveDir, OnDownloadListener listener) {
    this.url = url;
    this.saveDir = saveDir;
    Request request = new Request.Builder().url(url).build();
    this.listener = listener;
    okHttpClient.newCall(request).enqueue(this);
  }

  /**
   * @param saveDir
   * @return
   * @throws IOException 判断下载目录是否存在
   */
  private String isExistDir(String saveDir) throws IOException {
    // 下载位置
    File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
//    File downloadFile = new File(saveDir);
    if ( !downloadFile.mkdirs() ) {
      downloadFile.createNewFile();
    }
    String savePath = downloadFile.getAbsolutePath();
    return savePath;
  }

  /**
   * @param url
   * @return 从下载连接中解析出文件名
   */
  private String getNameFromUrl(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }

  public interface OnDownloadListener {
    /**
     * 下载成功
     */
    void onDownloadSuccess(File file);

    /**
     * @param percentage 下载进度
     */
    void onDownloading(double percentage);

    /**
     * 下载失败
     */
    void onDownloadFailed();
  }
}

