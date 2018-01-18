package com.seckawijoki.graduation_project.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/12 at 16:00.
 */

public class ViewUtils {
  private ViewUtils(){}
  public static void bindOnClick(View.OnClickListener listener, View ...views){
    for ( int i = 0 ; i < views.length ; i++ ) {
      views[i].setOnClickListener(listener);
    }
  }
  public static Class<ViewUtils> setText(Activity activity, int viewId, String text){
    ( (TextView) activity.findViewById(viewId) ).setText(text);
    return ViewUtils.class;
  }
  public static Class<ViewUtils> setText(Activity activity, int viewId, int stringId){
    ( (TextView) activity.findViewById(viewId) ).setText(stringId);
    return ViewUtils.class;
  }


}
