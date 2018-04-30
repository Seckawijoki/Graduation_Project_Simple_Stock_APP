package com.seckawijoki.graduation_project.functions.recommendations;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.UnderlineDecoration;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.functions.quotation_list.OnQuotationClickListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class RecommendationsViewImpl implements RecommendationsContract.View,
        OnQuotationClickListener, Toolbar.OnMenuItemClickListener {
  private static final String TAG = "RecommendationsViewImpl";
  private Activity activity;
  private Fragment fragment;
  private View view;
  private ActionCallback callback;
  private TopStockAdapter adapter;
  @Override
  public void initiate() {
    Toolbar tb = view.findViewById(R.id.tb_recommendations);
    ( (AppCompatActivity) activity ).setSupportActionBar(tb);
    tb.setOnMenuItemClickListener(this);
//    callback.onRequestHotStocks();
//    callback.onRequestTopStocks();
    new Handler().post(()->callback.onRequestTopStocks());
    new Handler().post(()->callback.onRequestHotStocks());
  }

  @Override
  public void destroy() {
    activity = null;
    fragment = null;
    callback = null;
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayTopStocks(List<ExpandableGroup> expandableGroupList) {
//    Log.d(TAG, "displayTopStocks()\n: expandableGroupList = " + expandableGroupList);
    RecyclerView rv = activity.findViewById(R.id.rv_top_stock);
    adapter = new TopStockAdapter(activity, expandableGroupList)
            .setOnQuotationClickListener(this);
    rv.setLayoutManager(new LinearLayoutManager(activity));
    rv.addItemDecoration(
            new UnderlineDecoration.Builder(activity)
                    .setPaddingLeft(activity.getResources().getDimension(R.dimen.w_stock_type)
                            + activity.getResources().getDimension(R.dimen.m_l_stock_name))
                    .setLineSizeRes(R.dimen.h_quotation_list_item_divider)
                    .build()
    );
    rv.setAdapter(adapter);

  }

  @Override
  public void displayHotStocks(List<Stock> stockList) {
    RecyclerView rv = activity.findViewById(R.id.rv_hot_stock);
    LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    rv.setLayoutManager(layoutManager);
    rv.setAdapter(new HotStockAdapter(activity).setStockList(stockList).setOnQuotationClickListener(this));
  }

  private RecommendationsViewImpl() {
  }

  RecommendationsViewImpl(Activity activity) {
    this.activity = activity;
  }

  RecommendationsViewImpl(Fragment fragment) {
    this.fragment = fragment;
    activity = fragment.getActivity();
    view = fragment.getView();
  }

  @Override
  public void onQuotationClick(long stockTableId) {
    Intent intent = new Intent(IntentAction.SINGLE_QUOTATION);
    intent.putExtra(IntentKey.STOCK_TABLE_ID, stockTableId);
    activity.startActivity(intent);
  }

  @Override
  public void onQuotationLongClick(Stock stock) {

  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch ( item.getItemId() ){
      case R.id.menu_refresh:
        new Handler().post(()->callback.onRequestTopStocks());
        new Handler().post(()->callback.onRequestHotStocks());
        break;
      case R.id.menu_search:
        activity.startActivity(new Intent(IntentAction.SEARCH));
        break;
        default:
          return false;
    }
    return true;
  }
}