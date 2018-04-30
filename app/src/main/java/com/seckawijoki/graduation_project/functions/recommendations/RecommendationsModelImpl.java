package com.seckawijoki.graduation_project.functions.recommendations;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.constants.server.TopStockOrder;
import com.seckawijoki.graduation_project.constants.server.TopStockType;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class RecommendationsModelImpl implements RecommendationsContract.Model {
  private static final String TAG = "RecommendationsModelImp";
  private RecommendationsContract.Model.DataCallback callback;
  private Activity activity;
  private static final int MSG_WHAT_TOP_STOCK = 0;
  private static final int MSG_WHAT_HOT_STOCK = 1;

  RecommendationsModelImpl(Activity activity) {
    this.activity = activity;
  }

  private final Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch ( msg.what ) {
        case MSG_WHAT_TOP_STOCK:
          callback.onDisplayTopStocks((List<ExpandableGroup>) msg.obj);
          break;
        case MSG_WHAT_HOT_STOCK:
          callback.onDisplayHotStocks((List<Stock>) msg.obj);
          break;
      }
    }
  };

  @Override
  public void initiate() {

  }

  @Override
  public void destroy() {
    callback = null;
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestTopStocks() {
    List<ExpandableGroup<Stock>> expandableGroupList = new LinkedList<>();
    String[] title = activity.getResources().getStringArray(R.array.array_top_stock_title);
    for ( int i = 0 ; i < title.length ; i++ ) {
      JSONArray jsonArray = getTopStockUrl(
              OkHttpUtils.post().url(ServerPath.GET_TOP_SEVERAL_STOCKS), i)
              .execute().jsonArray();
      List<Stock> stockList = new LinkedList<>();
      for ( int j = 0 ; j < jsonArray.length() ; ++j ) {
        try {
          JSONObject jsonObject = jsonArray.getJSONObject(j);
          Stock stock = new Stock()
                  .setStockTableId(jsonObject.getLong(MoJiReTsu.STOCK_TABLE_ID))
                  .setStockId(jsonObject.getString(MoJiReTsu.STOCK_ID))
                  .setStockName(jsonObject.getString(MoJiReTsu.STOCK_NAME))
                  .setCurrentPrice(jsonObject.getDouble(MoJiReTsu.CURRENT_PRICE))
                  .setFluctuationRate(jsonObject.getDouble(MoJiReTsu.FLUCTUATION_RATE))
                  .setTurnover(jsonObject.getDouble(MoJiReTsu.TURNOVER))
                  .setVolume(jsonObject.getLong(MoJiReTsu.VOLUME));
          stock.saveOrUpdate("stockTableId = ?", jsonObject.getString(MoJiReTsu.STOCK_TABLE_ID));
          stockList.add(stock);
        } catch ( JSONException e ) {
          e.printStackTrace();
        }
      }
      ExpandableGroup<Stock> expandableGroup = new ExpandableGroup<>(title[i], stockList);
      expandableGroupList.add(expandableGroup);
//        Log.d(TAG, "requestTopStocks()\n: expandableGroup = " + expandableGroup);
    }
    Message msg = Message.obtain(handler);
    msg.obj = expandableGroupList;
    msg.what = MSG_WHAT_TOP_STOCK;
    handler.sendMessage(msg);
  }

  @Override
  public void requestHotStocks() {
    List<Stock> stockList = new ArrayList<>();
    JSONArray jsonArray = OkHttpUtils.get().url(ServerPath.GET_HOT_STOCKS).execute().jsonArray();
      for ( int j = 0 ; j < jsonArray.length() ; ++j ) {
        try {
          JSONObject jsonObject = jsonArray.getJSONObject(j);
          Stock stock = new Stock()
                  .setStockTableId(jsonObject.getLong(MoJiReTsu.STOCK_TABLE_ID))
                  .setStockId(jsonObject.getString(MoJiReTsu.STOCK_ID))
                  .setStockName(jsonObject.getString(MoJiReTsu.STOCK_NAME))
                  .setCurrentPrice(jsonObject.getDouble(MoJiReTsu.CURRENT_PRICE))
                  .setCurrentPoint(jsonObject.getDouble(MoJiReTsu.CURRENT_POINT))
                  .setFluctuationRate(jsonObject.getDouble(MoJiReTsu.FLUCTUATION_RATE))
                  .setTurnover(jsonObject.getDouble(MoJiReTsu.TURNOVER))
                  .setVolume(jsonObject.getLong(MoJiReTsu.VOLUME));
          stock.saveOrUpdate("stockTableId = ?", jsonObject.getString(MoJiReTsu.STOCK_TABLE_ID));
          stockList.add(stock);
        } catch ( JSONException e ) {
          e.printStackTrace();
        }
    }
    Log.d(TAG, "requestHotStocks()\n: stockList = " + stockList);
    Message msg = Message.obtain(handler);
    msg.obj = stockList;
    msg.what = MSG_WHAT_HOT_STOCK;
    handler.sendMessage(msg);
  }

  private OkHttpUtils.PostBuilder getTopStockUrl(OkHttpUtils.PostBuilder postBuilder, int position) {
    postBuilder.addParam(MoJiReTsu.TOP_STOCK_COUNT, "8");
    switch ( position ) {
      case 3:
        postBuilder.addParam(MoJiReTsu.TOP_STOCK_ORDER, TopStockOrder.ASC + "");
        break;
      default:
        postBuilder.addParam(MoJiReTsu.TOP_STOCK_ORDER, TopStockOrder.DESC + "");
        break;
    }
    switch ( position ) {
      case 0:
        postBuilder.addParam(MoJiReTsu.TOP_STOCK_TYPE, TopStockType.TURNOVER + "");
        break;
      case 1:
        postBuilder.addParam(MoJiReTsu.TOP_STOCK_TYPE, TopStockType.VOLUME + "");
        break;
      case 2:
      case 3:
        postBuilder.addParam(MoJiReTsu.TOP_STOCK_TYPE, TopStockType.FLUCTUATION_RATE + "");
        break;
      case 4:
        postBuilder.addParam(MoJiReTsu.TOP_STOCK_TYPE, TopStockType.CURRENT_PRICE + "");
        break;

    }
    return postBuilder;
  }
}