package com.seckawijoki.graduation_project.utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/23.
 */

public class ToastUtils {
  private static Toast toast;
  private ToastUtils(){}
  public static void show(Context context, String text){
    if (toast == null){
      toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
    } else {
      toast.setText(text);
    }
    toast.show();
  }
  public static void show(Context context, int resourceId){
    try{
    if (toast == null){
      toast = Toast.makeText(context, resourceId, Toast.LENGTH_SHORT);
    } else {
      toast.setText(resourceId);
    }
    toast.show();
    } catch ( Resources.NotFoundException ignored ){

    }
  }
}
