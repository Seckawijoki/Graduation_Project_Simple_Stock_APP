package com.seckawijoki.graduation_project.functions.transaction;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.seckawijoki.graduation_project.db.client.UserTransaction;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:45.
 */

interface TransactionContract {
  interface View {
    void onPresenterInitiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayAllTransactions(List<UserTransaction> transactionList);
    interface ActionCallback {
      void onRequestAllTransactions();
    }
  }

  interface Model {
    void onViewInitiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestAllTransactions();
    interface DataCallback {
      void onDisplayAllTransactions(List<UserTransaction> transactionList);
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setView(Fragment fragment);
    Presenter setModel(Activity activity);
  }
}
