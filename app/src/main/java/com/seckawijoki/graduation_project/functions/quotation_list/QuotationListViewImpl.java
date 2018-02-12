package com.seckawijoki.graduation_project.functions.quotation_list;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.UnderlineDecoration;
import com.seckawijoki.graduation_project.constants.app.QuotationListSortType;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.constants.server.DefaultGroups;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.utils.ToastUtils;
import com.seckawijoki.graduation_project.utils.ViewUtils;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:37.
 */

final class QuotationListViewImpl implements QuotationListContract.View,
        OnQuotationClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
  private static final String TAG = "QuotationListViewImpl";
  private String groupName;
  private AppCompatActivity activity;
  private View view;
  private Fragment fragment;
  private ActionCallback callback;
  private QuotationListAdapter adapter;
  private SwipeRefreshLayout layoutRefresh;
  private RecyclerView rv;
  private ImageView imgStockNameSort;
  private ImageView imgCurrentPriceSort;
  private ImageView imgFluctuationRateSort;
  private View layoutAddNewStock;
  private TextView tvAddNewStock;
  private AlertDialog longClickDialog;
  private Stock stock;
  private boolean stockNameDesc;
  private boolean currentPriceDesc;
  private boolean fluctuationRateDesc;
  @Override
  public void onPresenterInitiate() {
    rv = view.findViewById(R.id.rv_stock_list);
    layoutAddNewStock = view.findViewById(R.id.layout_add_new_stock);
    ViewUtils.bindOnClick(this,
            imgStockNameSort = view.findViewById(R.id.img_quotation_stock_name_sort),
            imgCurrentPriceSort = view.findViewById(R.id.img_quotation_current_price_sort),
            imgFluctuationRateSort = view.findViewById(R.id.img_quotation_fluctuation_rate_sort),
            view.findViewById(R.id.img_quotation_full_screen),
            tvAddNewStock = view.findViewById(R.id.tv_add_new_stock));
    layoutRefresh = view.findViewById(R.id.layout_refresh_stock_list);
    imgStockNameSort.setVisibility(View.INVISIBLE);
    imgCurrentPriceSort.setVisibility(View.INVISIBLE);
    imgFluctuationRateSort.setVisibility(View.INVISIBLE);
    layoutRefresh.setOnRefreshListener(this);
    layoutRefresh.setColorSchemeResources(
            R.color.bg_stock_grey,
            R.color.bg_stock_green,
            R.color.bg_stock_red
    );
    adapter = new QuotationListAdapter(activity)
            .setOnQuotationClickListener(this);
    rv.setLayoutManager(new LinearLayoutManager(activity));
    Resources resources = activity.getResources();
    UnderlineDecoration itemDecoration = new UnderlineDecoration.Builder(activity)
            .setPaddingLeft(resources.getDimension(R.dimen.w_stock_type) + resources.getDimension(R.dimen.m_l_stock_name))
            .setLineSizeRes(R.dimen.h_quotation_list_item_divider)
            .build();
//    rv.addItemDecoration(new QuotationListItemDecoration(activity));
    rv.addItemDecoration(itemDecoration);
    callback.onRequestQuotationList();
    rv.setAdapter(adapter);
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
  public void displayQuotationList(List<Stock> stockList) {
    activity.runOnUiThread(() -> {
      if (longClickDialog != null) {
        longClickDialog.dismiss();
      }
      if (stockList == null || stockList.size() <= 0) {
        rv.setVisibility(View.GONE);
        layoutAddNewStock.setVisibility(View.VISIBLE);
        tvAddNewStock.setOnClickListener(this);
      } else {
        rv.setVisibility(View.VISIBLE);
        layoutAddNewStock.setVisibility(View.GONE);
        tvAddNewStock.setOnClickListener(null);
        adapter.setStockList(stockList)
                .notifyDataSetChanged();
      }
      layoutRefresh.setRefreshing(false);
    });
  }

  @Override
  public void displayDeleteFavoriteStock(Stock stock) {
    adapter.deleteFavoriteStock(stock).notifyDataSetChanged();
    if (longClickDialog != null) {
      longClickDialog.dismiss();
    }
  }

  @Override
  public void displaySetFavoriteStockTop(Stock stock) {
    adapter.setFavoriteStockTop(stock).notifyDataSetChanged();
    if (longClickDialog != null) {
      longClickDialog.dismiss();
    }
  }

  @Override
  public void displaySetSpecialAttention(Stock stock) {
    adapter.setSpecialAttention(stock).notifyDataSetChanged();
    if (longClickDialog != null) {
      longClickDialog.dismiss();
    }
  }

  @Override
  public void displayNotifyAdapter() {
    if (longClickDialog != null) {
      longClickDialog.dismiss();
    }
    adapter.notifyDataSetChanged();
  }


  @Override
  public void displayToast(int msgId) {
    ToastUtils.show(activity, msgId);
  }

  private QuotationListViewImpl() {
  }

  QuotationListViewImpl(AppCompatActivity activity) {
    this.activity = activity;
  }

  QuotationListViewImpl(Fragment fragment, String groupName) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
    this.groupName = groupName;
  }

  @Override
  public void onQuotationClick(long stockTableId) {
    Intent intent = new Intent(IntentAction.SINGLE_QUOTATION);
    intent.putExtra(IntentKey.STOCK_TABLE_ID, stockTableId);
    fragment.startActivityForResult(intent, ActivityRequestCode.SINGLE_QUOTATION);
  }

  @Override
  public void onQuotationLongClick(Stock stock) {
    if (stock == null){
      callback.onRequestQuotationList();
    }
    this.stock = stock;
    View view = activity.getLayoutInflater().inflate(R.layout.alert_dialog_on_quotation_long_click, null);
    TextView tvDelete = view.findViewById(R.id.tv_delete);
    TextView tvSetSpecialAttention = view.findViewById(R.id.tv_set_special_attention);
    if (stock.isSpecialAttention()){
      tvSetSpecialAttention.setText(R.string.action_cancel_special_attention);
    }
    if ( TextUtils.equals(groupName, DefaultGroups.SPECIAL_ATTENTION)){
      tvDelete.setVisibility(View.GONE);
    }
    ViewUtils.bindOnClick(this,
            tvDelete,
            view.findViewById(R.id.tv_set_top),
            tvSetSpecialAttention,
            view.findViewById(R.id.tv_set_special_attention));
    longClickDialog = new AlertDialog.Builder(activity, R.style.MyAlertDialog)
            .setTitle(stock.getStockName())
            .setView(view)
            .create();
    longClickDialog.show();
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ){
      case R.id.img_quotation_stock_name_sort:
        adapter.sort(QuotationListSortType.STOCK_NAME, true);
        break;
      case R.id.img_quotation_current_price_sort:

        break;
      case R.id.img_quotation_fluctuation_rate_sort:

        break;
      case R.id.img_quotation_full_screen:

        break;
      case R.id.tv_add_new_stock:

        break;
      case R.id.tv_delete:
        longClickDialog.dismiss();
        callback.onRequestDeleteFavoriteStock(stock);
        break;
      case R.id.tv_set_top:
        longClickDialog.dismiss();
        callback.onRequestSetFavoriteStockTop(stock);
        break;
      case R.id.tv_set_special_attention:
        longClickDialog.dismiss();
        callback.onRequestSetSpecialAttention(stock);
        break;
      case R.id.tv_bulk_edition:
        longClickDialog.dismiss();

        break;
    }
  }

  @Override
  public void onRefresh() {
    layoutRefresh.setRefreshing(true);
    callback.onRequestQuotationList();
  }
}