package com.seckawijoki.graduation_project.functions.transaction;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:56.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.seckawijoki.graduation_project.db.client.UserTransaction;

import java.util.List;

final class TransactionPresenterImpl implements TransactionContract.Presenter,
        TransactionContract.View.ActionCallback,
        TransactionContract.Model.DataCallback {
  private TransactionContract.View view;
  private TransactionContract.Model model;

  @Override
  public TransactionContract.Presenter setView(TransactionContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public TransactionContract.Presenter setView(Fragment fragment) {
    this.view = new TransactionViewImpl(fragment);
    return this;
  }


  @Override
  public TransactionContract.Presenter setModel(Activity activity) {
    this.model = new TransactionModelImpl();
    return this;
  }

  @Override
  public TransactionContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.onPresenterInitiate();
    model.onViewInitiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestAllTransactions() {
    model.requestAllTransactions();
  }

  @Override
  public void onDisplayAllTransactions(List<UserTransaction> transactionList) {
    view.displayAllTransactions(transactionList);
  }
}