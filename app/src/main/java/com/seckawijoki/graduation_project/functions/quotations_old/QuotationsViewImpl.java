package com.seckawijoki.graduation_project.functions.quotations_old;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.Quotation;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.functions.quotation_list.OnQuotationClickListener;
import com.seckawijoki.graduation_project.functions.quotation_list.QuotationListAdapter;

import java.util.List;


/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class QuotationsViewImpl implements QuotationsContract.View, View.OnClickListener, OnQuotationClickListener {
  private static final String TAG = "QuotationsViewImpl";
  private Fragment fragment;
  private Activity activity;
  private ActionCallback callback;
  private QuotationListAdapter adapter;
  private RecyclerView rvQuotations;
  private TextView tv;
  @Override
  public void initiate() {
    /*
    tv = activity.findViewById(R.id.tv_temporary_quotations_title);
    adapter = new QuotationListAdapter(activity).setOnQuotationClickListener(this);
    rvQuotations = activity.findViewById(R.id.rv_quotation_list);
    LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rvQuotations.setLayoutManager(layoutManager);
    rvQuotations.setAdapter(adapter);
    tv.setOnClickListener(this);
    callback.onRequestUpdateQuotationList();
    */
  }

  @Override
  public void destroy() {
    activity = null;
    callback = null;
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayUpdateQuotationList(List<Quotation> stockList) {
//    Log.d(TAG, "displayUpdateStockList(): ");
    /*
    if (adapter == null){
      adapter = new QuotationListAdapter(activity, stockList);
      rvQuotations.setAdapter(adapter);
    }
    adapter.setStockList(stockList);
    activity.runOnUiThread(()->{
      tv.setEnabled(true);
      adapter.notifyDataSetChanged();
    });
    */
  }

  private QuotationsViewImpl() {
  }

  QuotationsViewImpl(Activity activity) {
    this.activity = activity;
  }

  QuotationsViewImpl(Fragment fragment) {
    this.fragment = fragment;
    activity = fragment.getActivity();
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ){
      case R.id.tv_temporary_quotations_title:
//        callback.onRequestUpdateQuotationList();
//        tv.setEnabled(false);
        break;
    }
  }

  @Override
  public void onQuotationClick(long stockTableId) {
    /*
    QuotationDetailsViewImpl fragment = QuotationDetailsViewImpl.newInstance();
    Bundle bundle = fragment.getArguments();
    bundle.putString(BundleKey.STOCK_TABLE_ID, stockTableId);
    fragment.setArguments(bundle);
    fragment.show(this.fragment.getFragmentManager(), FragmentTag.QUOTATION_DETAIL);
    */
  }

  @Override
  public void onQuotationLongClick(Stock stock) {

  }
}