package com.seckawijoki.graduation_project.functions.transaction;

import android.util.Log;

import com.seckawijoki.graduation_project.constants.app.FilePath;
import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.client.User;
import com.seckawijoki.graduation_project.db.client.UserTransaction;
import com.seckawijoki.graduation_project.tools.FileTools;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:45.
 */

final class TransactionModelImpl implements TransactionContract.Model {
  private static final String TAG = "TransactionModelImpl";
  private DataCallback callback;

  @Override
  public void onViewInitiate() {

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
  public void requestAllTransactions() {
    JSONArray jsonArray = OkHttpUtils.get()
            .url(ServerPath.GET_ALL_TRANSACTIONS)
            .execute()
            .jsonArray();
    UserTransaction[] transactions = new UserTransaction[jsonArray.length()];
    for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
      try {

        JSONObject jsonObject = jsonArray.getJSONObject(i);
        transactions[i] = new UserTransaction()
                .setJsonObject(jsonObject);
        transactions[i].saveOrUpdate("transactionId = ?",
                jsonObject.getString(MoJiReTsu.TRANSACTION_ID));
      } catch ( JSONException e ) {
        e.printStackTrace();
      }
    }
    for ( int i = 0 ; i < transactions.length ; i++ ) {
      UserTransaction transaction = transactions[i];
      String portraitUri = requestUserPortrait(transaction.getUserId());
      Log.d(TAG, "requestAllTransactions()\n: portraitUri = " + portraitUri);
      transaction.setPortraitUri(portraitUri)
              .saveOrUpdate("transactionId = ?", transaction.getTransactionId()+"");
    }
    requestAllTransactionsFromDatabase();
  }

  private String requestUserPortrait(long userId){
    File portraitFile = OkHttpUtils.getFile()
            .url(ServerPath.GET_USER_PORTRAIT)
            .fileName(FileTools.getPortraitFileName(userId))
            .savePath(FilePath.USER_PORTRAIT_PATH)
            .addParam(MoJiReTsu.USER_ID, userId+"")
            .executeForFile();
    return portraitFile.getPath();
  }

  private void requestAllTransactionsFromDatabase(){
    List<UserTransaction> transactionList = DataSupport
            .order("tradeDateTime desc")
            .find(UserTransaction.class);
//            .find(UserTransaction.class);
    Log.d(TAG, "requestAllTransactionsFromDatabase()\n: transactionList = " + transactionList);
    callback.onDisplayAllTransactions(transactionList);
  }
}