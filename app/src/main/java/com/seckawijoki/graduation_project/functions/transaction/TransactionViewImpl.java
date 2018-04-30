package com.seckawijoki.graduation_project.functions.transaction;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.UnderlineDecoration;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.client.UserTransaction;
import com.seckawijoki.graduation_project.functions.assets.OnFollowToBuyOrSellListener;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:45.
 */

final class TransactionViewImpl implements TransactionContract.View, OnTransactionClickListener, SwipeRefreshLayout.OnRefreshListener {
  private AppCompatActivity activity;
  private View view;
  private Fragment fragment;
  private ActionCallback callback;
  private SwipeRefreshLayout layoutRefresh;
  private TransactionAdapter adapter;
  private OnFollowToBuyOrSellListener listener;
  TransactionViewImpl setOnFollowToBuyOrSellListener(OnFollowToBuyOrSellListener listener) {
    this.listener = listener;
    return this;
  }

  @Override
  public void onPresenterInitiate() {
    layoutRefresh = view.findViewById(R.id.layout_refresh_transaction);
    layoutRefresh.setOnRefreshListener(this);
    RecyclerView rv = view.findViewById(R.id.rv_transaction);
    rv.setLayoutManager(new LinearLayoutManager(activity));
    adapter = new TransactionAdapter(activity).setOnTransactionClickListener(this);
    rv.setAdapter(adapter);
    rv.addItemDecoration(
            new UnderlineDecoration.Builder(activity)
                    .setPaddingLeft(
                            activity.getResources().getDimension(R.dimen.w_h_transaction_user_portrait)
                            + activity.getResources().getDimension(R.dimen.p_l_r_layout_list_item_transaction)
                            + activity.getResources().getDimension(R.dimen.m_l_transaction_user_nickname)
                    )
                    .setLineSizeRes(R.dimen.h_transaction_list_item_divider)
                    .build()
    );
    callback.onRequestAllTransactions();
    ToastUtils.show(activity, R.string.msg_under_loading_transactions);
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
  public void displayAllTransactions(List<UserTransaction> transactionList) {
    layoutRefresh.setRefreshing(false);
    if (adapter == null){
      adapter = new TransactionAdapter(activity).setOnTransactionClickListener(this);
      ( (RecyclerView) view.findViewById(R.id.rv_transaction) ).setAdapter(adapter);
    }
    ToastUtils.show(activity, R.string.msg_succeeded_in_loading_transactions);
    adapter.setTransactionList(transactionList).notifyDataSetChanged();
  }

  private TransactionViewImpl() {
  }

  TransactionViewImpl(Activity activity) {
    this.activity = (AppCompatActivity) activity;
  }

  TransactionViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }

  @Override
  public void onStockClick(long stockTableId) {
    Intent intent = new Intent(IntentAction.SINGLE_QUOTATION);
    intent.putExtra(IntentKey.STOCK_TABLE_ID, stockTableId);
    activity.startActivityForResult(
            intent,
            ActivityRequestCode.SINGLE_QUOTATION
    );
  }

  @Override
  public void onFollowToBuyOrSell(UserTransaction transaction) {
    listener.onFollowToBuyOrSell(transaction);
  }

  @Override
  public void onRefresh() {
    layoutRefresh.setRefreshing(true);
    callback.onRequestAllTransactions();
    ToastUtils.show(activity, R.string.msg_under_loading_transactions);
  }
}