package com.seckawijoki.graduation_project.functions.about_us;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.FilePath;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/10 at 11:02.
 */

public class AppUpgradeService extends Service {
  private static final String TAG = "AppUpgradeService";
  private ApkDownloadTask apkDownloadTask;

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if ( intent == null ) {
      stopSelf();
      Log.e(TAG, "onStartCommand()\n: ");
      return super.onStartCommand(null, flags, startId);
    }
    Log.i(TAG, "onStartCommand()\n: ");
    Log.d(TAG, "onStartCommand()\n: Thread.currentThread().getId() = " + Thread.currentThread().getId());
    /*
    mDownloadUrl = ServerPath.GET_LATEST_APK;
    if ( Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) ) {
      directory = new File(Environment.getExternalStorageDirectory().getPath() + DOWNLOAD_PATH);
      if ( directory.exists() ) {
        File destFile = new File(directory.getPath() + "/" + URLEncoder.encode(mDownloadUrl));
        if ( destFile.exists() && destFile.isFile() && checkApkFile(destFile.getPath()) ) {
          Log.w(TAG, "onStartCommand()\n: Apk existed.");
          install(destFile);
          stopSelf();
          return super.onStartCommand(intent, flags, startId);
        }
      }
    } else {
      return super.onStartCommand(intent, flags, startId);
    }
    */
    apkDownloadTask = new ApkDownloadTask();
    apkDownloadTask.execute();
    return super.onStartCommand(intent, flags, startId);

  }

  private class ApkDownloadTask extends AsyncTask<Void, Double, File> {
    private static final String TAG = "ApkDownloadTask";
    private NotificationManager mNotificationManager = null;
    private Notification.Builder notificationBuilder;
    private Notification mNotification = null;
    private PendingIntent mPendingIntent = null;
    private File directory = null;
    private int notificationId;
    private static final String DOWNLOAD_PATH = FilePath.BASE_PATH;
    private String currentProgress;
    private int maxProgress;
    private int updateTime;

    /**
     * apk文件安装
     *
     * @param apkFile apk文件
     */
    private void install(File apkFile) {
      Uri uri = Uri.fromFile(apkFile);
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      Log.d(TAG, "install()\n: uri = " + uri.toString());
      intent.setDataAndType(uri, "application/vnd.android.package-archive");
      startActivity(intent);
    }

    private void initiateNotification() {
      mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      Intent completingIntent = new Intent();
      completingIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
      completingIntent.setClass(getApplicationContext(), AppUpgradeService.class);
      // 创建Notifcation对象，设置图标，提示文字,策略
      mPendingIntent = PendingIntent.getActivity(
              AppUpgradeService.this,
              R.string.app_name,
              completingIntent,
              PendingIntent.FLAG_UPDATE_CURRENT
      );
      mNotification = new Notification();
      Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_launcher);
      Bitmap bitmap = ( (BitmapDrawable) drawable ).getBitmap();
      notificationBuilder = new Notification.Builder(getApplicationContext())
              .setTicker(getApplicationContext().getString(R.string.msg_start_downloading))
              .setSmallIcon(R.mipmap.ic_launcher)
              .setLargeIcon(bitmap)
              .setContentTitle(getApplicationContext().getString(R.string.msg_under_downloading))
              .setContentText(getApplicationContext().getString(R.string.format_current_downloading_progress))
              .setProgress(maxProgress, 0, false)
              .setContentIntent(mPendingIntent)
              .setAutoCancel(false)
              .setOngoing(false);
      /*
      mNotification.contentView = new RemoteViews(              getPackageName(),              R.layout.notification_apk_download);
      mNotification.contentView.setProgressBar(              R.id.pb_apk_download,              10000,              0,              false);
      mNotification.contentView.setTextViewText(              R.id.tv_apk_download_title,              getApplicationContext().getString(R.string.msg_under_downloading));
      mNotification.contentView.setTextViewText(              R.id.tv_apk_download_progress,              String.format(currentProgress, 0.00));
      */
      mNotificationManager.cancel(notificationId);
      mNotificationManager.notify(notificationId, mNotification = notificationBuilder.build());
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      Log.d(TAG, "onPreExecute()\n: Thread.currentThread().getId() = " + Thread.currentThread().getId());
      updateTime = 0;
      Resources resources = getApplicationContext().getResources();
      currentProgress = resources.getString(R.string.format_current_downloading_progress);
      maxProgress = resources.getInteger(R.integer.int_max_downloading_progress);
      notificationId = resources.getInteger(R.integer.notification_id_apk_download);
      initiateNotification();
    }


    @Override
    protected File doInBackground(Void... voids) {
      Log.d(TAG, "doInBackground()\n: Thread.currentThread().getId() = " + Thread.currentThread().getId());
      if ( Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) ) {
        if ( directory == null ) {
          directory = new File(Environment.getExternalStorageDirectory().getPath() + DOWNLOAD_PATH);
        }
        if ( directory.exists() || directory.mkdirs() ) {
          String fileName = getApplicationContext().getString(R.string.app_name) + ".apk";
//          fileName = URLEncoder.encode(DOWNLOAD_URL);
          File apkFile = new File(directory.getPath() + "/" + fileName);
          /*
            if ( apkFile.exists() && apkFile.isFile() && checkApkFile(apkFile.getPath()) )
              install(apkFile);
           else
             */
          ResponseBody responseBody = OkHttpUtils.post()
                  .url(ServerPath.GET_LATEST_APK)
                  .executeForResponse()
                  .body();
          byte[] buffers = new byte[2048];
          long contentLength = responseBody.contentLength();
          Log.d(TAG, "doInBackground()\n: contentLength = " + contentLength);
          long unit = contentLength / 20;
          long currentLength = 0;
          int length;
          InputStream is = null;
          FileOutputStream fos = null;
          int updateTime = 0;
          try {
            is = responseBody.byteStream();
            fos = new FileOutputStream(apkFile);
            while ( ( length = is.read(buffers) ) != -1 ) {
              fos.write(buffers, 0, length);
              currentLength += length;
              ++updateTime;
              if (updateTime >= 100 ) {
                updateTime = 0;
                publishProgress(currentLength * 100.0 / contentLength);
              }
            }
            fos.flush();
            return apkFile;
          } catch ( IOException e ) {
            e.printStackTrace();
            if ( is != null ) {
              try {
                is.close();
              } catch ( IOException e1 ) {
                e1.printStackTrace();
              }
            }
            if ( fos != null ) {
              try {
                fos.close();
              } catch ( IOException e1 ) {
                e1.printStackTrace();
              }
            }
          }
        }
      }
      stopSelf();
      return null;
    }

    @Override
    protected void onProgressUpdate(Double... values) {
      super.onProgressUpdate(values);
      double percentage = values[0];
//      Log.d(TAG, "onProgressUpdate()\n: percentage = " + percentage);
//      Log.d(TAG, "onProgressUpdate()\n: Thread.currentThread().getId() = " + Thread.currentThread().getId());
      notificationBuilder.setProgress(maxProgress, (int) ( percentage * 100 ), false);
      notificationBuilder.setContentText(String.format(currentProgress, percentage));
      /*
      mNotification.contentView.setProgressBar(              R.id.pb_apk_download,              maxProgress,              (int) percentage * 100,false  );
      mNotification.contentView.setTextViewText(              R.id.tv_apk_download_progress,              String.format(currentProgress, percentage) );
      */
      mNotificationManager.notify(notificationId, mNotification = notificationBuilder.build());
    }

    @Override
    protected void onPostExecute(File file) {
      Log.d(TAG, "onPostExecute()\n: Thread.currentThread().getId() = " + Thread.currentThread().getId());
      super.onPostExecute(file);
      mNotification.defaults = Notification.DEFAULT_SOUND;
      mNotification.contentIntent = mPendingIntent;
      notificationBuilder.setProgress(maxProgress, 10000, false);
      notificationBuilder.setContentText(String.format(currentProgress, 100.00));

      /*
      mNotification.contentView.setTextViewText(
              R.id.tv_apk_download_title,
              getApplicationContext().getString(R.string.msg_finish_downloading)
      );
      mNotification.contentView.setTextViewText(
              R.id.tv_apk_download_progress,
              String.format(currentProgress, 100.00)
      );
      */
      mNotificationManager.notify(notificationId, mNotification);
      ToastUtils.show(getApplicationContext(), R.string.msg_succeeded_in_downloading);
      install(file);
      mNotificationManager.cancel(notificationId);
    }


    /**
     * 判断文件是否完整
     *
     * @param apkFilePath apk文件路径
     * @return
     */
    private boolean checkApkFile(String apkFilePath) {
      boolean result;
      try {
        PackageManager pManager = getPackageManager();
        PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
        if ( pInfo == null ) {
          result = false;
        } else {
          result = true;
        }
      } catch ( Exception e ) {
        result = false;
        e.printStackTrace();
      }
      return result;
    }

  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
