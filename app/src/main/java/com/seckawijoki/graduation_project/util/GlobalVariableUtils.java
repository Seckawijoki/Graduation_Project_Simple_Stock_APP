package com.seckawijoki.graduation_project.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.PreferenceKey;

import static okhttp3.internal.Internal.instance;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/2 at 21:24.
 */

public class GlobalVariableUtils {
  private static final String TAG = "GlobalVariableUtils";
  private Activity activity;
  private GlobalVariableUtils() {
  }
  public static boolean setMainFragment(Activity activity, int position){
    return putInt(activity, PreferenceKey.MAIN_FRAGMENT, position);
  }
  public static int getMainFragment(Activity activity){
    SharedPreferences sp = activity.getSharedPreferences(
            activity.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    return sp.getInt(PreferenceKey.MAIN_FRAGMENT, 2);
  }
  public static boolean setAutoLogin(Activity activity, boolean auto){
    return putBoolean(activity, PreferenceKey.AUTO_LOGIN, auto);
  }
  public static boolean getAutoLogin(Activity activity){
    SharedPreferences sp = activity.getSharedPreferences(
            activity.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    return sp.getBoolean(PreferenceKey.AUTO_LOGIN, false);
  }
  public static boolean setAccount(Activity activity, String account){
    /*
    new GlobalVariables()
            .setLoggedInAccount(account)
            .saveOrUpdate("idOnly = ?", GlobalVariables.ID_ONLY);
    */
    return putString(activity, PreferenceKey.ACCOUNT, account);
  }
  public static String getAccount(Activity activity){
    SharedPreferences sp = activity.getSharedPreferences(
            activity.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    return sp.getString(PreferenceKey.ACCOUNT, "");
    /*
    return DataSupport
            .findFirst(GlobalVariables.class)
            .loggedInAccount;
    */
  }
  public static boolean setUserId(Activity activity, long userId){
    return putLong(activity, PreferenceKey.USER_ID, userId);
  }
  public static String getUserId(Activity activity){
    SharedPreferences sp = activity.getSharedPreferences(
            activity.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    // TODO: 2018/1/3
    return sp.getLong(PreferenceKey.USER_ID, 1) + "";
  }
  private static boolean putBoolean(Activity activity, String key, boolean b){
    SharedPreferences sp = activity.getSharedPreferences(
            activity.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    SharedPreferences.Editor editor = sp.edit();
    editor.putBoolean(key, b);
    return editor.commit();
  }

  private static boolean putString(Activity activity, String key, String value){
    SharedPreferences sp = activity.getSharedPreferences(
            activity.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(key, value);
    return editor.commit();
  }
  private static boolean putInt(Activity activity, String key, int value){
    SharedPreferences sp = activity.getSharedPreferences(
            activity.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    SharedPreferences.Editor editor = sp.edit();
    editor.putInt(key, value);
    return editor.commit();
  }
  private static boolean putLong(Activity activity, String key, long value){
    SharedPreferences sp = activity.getSharedPreferences(
            activity.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    SharedPreferences.Editor editor = sp.edit();
    editor.putLong(key, value);
    return editor.commit();
  }
}
