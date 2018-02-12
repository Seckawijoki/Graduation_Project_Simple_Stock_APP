package com.seckawijoki.graduation_project.tools;

import android.util.Log;

import com.seckawijoki.graduation_project.db.client.LoggedInUsers;
import com.seckawijoki.graduation_project.db.Quotation;
import com.seckawijoki.graduation_project.db.QuotationDetails;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.db.server.FavoriteStock;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/15 at 17:08.
 */

public class DbLogTools {
  private static final String TAG = "DbLogTools";
  private DbLogTools(){}
  public static void logDataSupport(Class c){
    List<Object> objectList = DataSupport.findAll(c);
    for ( int j = 0 ; j < objectList.size() ; j++ ) {
      Log.d(TAG, "logDataSupport(): " + objectList.get(j).toString());
    }
  }
  public static void logAllDataSupport(){
    List<Class> classList = new ArrayList<>();
    classList.add(FavoriteStock.class);
    classList.add(Stock.class);
    for ( int i = 0 ; i < classList.size() ; ++i ) {
      logDataSupport(classList.get(i));
    }
  }
  public static void logCount(Class c) {
    Log.d(TAG, "onDetach(): DataSupport.count(" + c.getSimpleName() + ") = "
            + DataSupport.count(c));
  }

  public static void logCount(){
    Log.d(TAG, "onDetach(): DataSupport.count(LoggedInUsers.class) = "
            + DataSupport.count(LoggedInUsers.class));
    Log.d(TAG, "onDetach(): DataSupport.count(Quotation.class) = "
            + DataSupport.count(Quotation.class));
    Log.d(TAG, "onDetach(): DataSupport.count(QuotationDetails.class) = "
            + DataSupport.count(QuotationDetails.class));
    Log.d(TAG, "onDetach(): DataSupport.count(Stock.class) = "
            + DataSupport.count(Stock.class));
  }
}
