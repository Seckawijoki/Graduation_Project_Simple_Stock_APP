package com.seckawijoki.graduation_project.functions.quotation_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.QuotationListSortType;
import com.seckawijoki.graduation_project.constants.common.ActivityIntent;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.BundleKey;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.constants.server.DefaultGroups;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.util.ToastUtils;
import com.seckawijoki.graduation_project.util.ViewUtils;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:37.
 */

final class QuotationListViewImpl implements QuotationListContract.View,
        OnQuotationClickListener, View.OnClickListener{
  private static final String TAG = "QuotationListViewImpl";
  private String groupName;
  private AppCompatActivity activity;
  private View view;
  private Fragment fragment;
  private ActionCallback callback;
  private QuotationListAdapter adapter;
  private RecyclerView rv;
  private ImageView imgStockNameSort;
  private ImageView imgCurrentPriceSort;
  private ImageView imgFluctuationRateSort;
  private ViewGroup layoutAddNewStock;
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
    imgStockNameSort.setVisibility(View.INVISIBLE);
    imgCurrentPriceSort.setVisibility(View.INVISIBLE);
    imgFluctuationRateSort.setVisibility(View.INVISIBLE);
    adapter = new QuotationListAdapter(activity)
            .setOnQuotationClickListener(this);
    rv.setLayoutManager(new LinearLayoutManager(activity));
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
    Intent intent = new Intent(ActivityIntent.THE_QUOTATION);
    Bundle bundle = new Bundle();
    bundle.putLong(BundleKey.STOCK_TABLE_ID, stockTableId);
    intent.putExtra(IntentKey.THE_QUOTATION, bundle);
    fragment.startActivityForResult(intent, ActivityRequestCode.THE_QUOTATION);
  }

  @Override
  public void onQuotationLongClick(Stock stock) {
    this.stock = stock;
    View view = activity.getLayoutInflater().inflate(R.layout.dialog_on_quotation_long_click, null);
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
    longClickDialog = new AlertDialog.Builder(activity)
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
}