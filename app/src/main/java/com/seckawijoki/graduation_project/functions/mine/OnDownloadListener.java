package com.seckawijoki.graduation_project.functions.mine;

import android.os.Parcelable;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/11 at 21:59.
 */

public interface OnDownloadListener extends Parcelable {
  void onDownloadSuccess();
  void onDownloadProgress(double percentage);
  void onDownloadFailed();
}
