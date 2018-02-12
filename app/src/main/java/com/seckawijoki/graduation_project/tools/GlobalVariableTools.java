package com.seckawijoki.graduation_project.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.PreferenceKey;
import com.seckawijoki.graduation_project.constants.server.StockType;

import static okhttp3.internal.Internal.instance;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/2 at 21:24.
 */

public class GlobalVariableTools {
  private static final String TAG = "GlobalVariableTools";
  public static String getStockType(Context context, int stockType) {
    int res;
    switch ( stockType ) {
      default:
        res = R.string.default_value;
      case StockType.SH:
        res = R.string.label_sh;
      case StockType.SZ:
        res = R.string.label_sz;
    }
    return context.getString(res);
  }
  private GlobalVariableTools() {
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
  public static String getUserId(Context context){
    SharedPreferences sp = context.getSharedPreferences(
            context.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    return sp.getLong(PreferenceKey.USER_ID, 1) + "";
  }
  public static boolean setTradeStockTableId(Context context, long stockTableId){
    return putLong(context, PreferenceKey.TRADE_STOCK_TABLE_ID, stockTableId);
  }
  public static long getTradeStockTableId(Context context){
    SharedPreferences sp = context.getSharedPreferences(
            context.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    return sp.getLong(PreferenceKey.TRADE_STOCK_TABLE_ID, 1);
  }
  private static boolean putBoolean(Context context, String key, boolean b){
    SharedPreferences sp = context.getSharedPreferences(
            context.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    SharedPreferences.Editor editor = sp.edit();
    editor.putBoolean(key, b);
    return editor.commit();
  }

  private static boolean putString(Context context, String key, String value){
    SharedPreferences sp = context.getSharedPreferences(
            context.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(key, value);
    return editor.commit();
  }
  private static boolean putInt(Context context, String key, int value){
    SharedPreferences sp = context.getSharedPreferences(
            context.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    SharedPreferences.Editor editor = sp.edit();
    editor.putInt(key, value);
    return editor.commit();
  }
  private static boolean putLong(Context context, String key, long value){
    SharedPreferences sp = context.getSharedPreferences(
            context.getString(R.string.app_name), Context.MODE_PRIVATE
    );
    SharedPreferences.Editor editor = sp.edit();
    editor.putLong(key, value);
    return editor.commit();
  }
}
