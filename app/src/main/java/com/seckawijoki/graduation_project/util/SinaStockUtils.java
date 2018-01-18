package com.seckawijoki.graduation_project.util;

import android.text.TextUtils;

import com.seckawijoki.graduation_project.constants.server.StockType;
import com.seckawijoki.graduation_project.constants.sina.SinaStockType;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/13 at 18:59.
 */

public class SinaStockUtils {
  private SinaStockUtils(){}
  public static int parseStockType(String result){
    int end = result.indexOf('=');
    String type = result.substring(end-8, end-6);
    if ( TextUtils.equals(type, SinaStockType.SH)){
      return StockType.SH;
    } else if (TextUtils.equals(type, SinaStockType.SZ)){
      return StockType.SZ;
    } else {
      return -1;
    }
  }
  public static String extractStockId(String result){
    int end = result.indexOf('=');
    return result.substring(end-6, end);
  }
  public static String[] parse(String response){
    int start = response.indexOf('"') + 1;
    int end = response.lastIndexOf('"');
    return response.substring(start, end).split(",");
  }
}
