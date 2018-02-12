package com.seckawijoki.graduation_project.functions.login;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.server.LoginStatus;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.client.LoggedInUsers;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/12 at 10:12.
 */

class LoginModelImpl implements LoginContract.Model {
  private static final String TAG = "LoginModelImpl";
  private DataCallback callback;
  private LoginAsyncTask loginAsyncTask;
  private OkHttpClient okHttpClient = new OkHttpClient();
  LoginModelImpl(Activity activity) {
    this.activity = activity;
  }
  private Activity activity;
  @Override
  public void onViewInitiate() {

  }

  @Override
  public void requestAutoLogin(String mac){
    LoggedInUsers loggedInUsers =
            DataSupport.select(LoggedInUsers.ACCOUNT, LoggedInUsers.PASSWORD)
            .order(LoggedInUsers.LAST_LOGIN_TIME + " desc")
            .findFirst(LoggedInUsers.class);
    if (loggedInUsers == null)
      return;
    callback.onDisplayAutoLogin(
            loggedInUsers.getAccount(),
            loggedInUsers.getPassword());
  }

  @Override
  public void destroy() {
    callback = null;
    if ( loginAsyncTask != null ) {
      loginAsyncTask.cancel(true);
      loginAsyncTask = null;
    }
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestLogin(String account, String password, String mac) {
    if ( loginAsyncTask != null ) return;
    loginAsyncTask = new LoginAsyncTask(account, password, mac);
    loginAsyncTask.execute((Void) null);
    Log.i(TAG, "requestLogin(): ");
  }

  @Override
  public void requestAccountList() {
    List<String> accountList;
    List<LoggedInUsers> loggedInUsersList;
    loggedInUsersList = DataSupport.findAll(LoggedInUsers.class);
    accountList = new ArrayList<>(loggedInUsersList.size());
    for ( int i = 0 ; i < loggedInUsersList.size() ; i++ ) {
      LoggedInUsers loggedInUsers = loggedInUsersList.get(i);
      accountList.add(loggedInUsers.getAccount());
    }
    Log.d(TAG, "requestAccountList(): accountList = " + accountList);
    loggedInUsersList.clear();
    callback.onDisplayAccountList(accountList);
  }



  /**
   * Represents an asynchronous login/registration task used to authenticate
   * the user.
   */
  private class LoginAsyncTask extends AsyncTask<Void, Void, JSONObject> {
    private static final String TAG = "LoginAsyncTask";
    private final String mAccount;
    private final String mPassword;
    private final String mMac;

    private LoginAsyncTask(String email, String password, String mac) {
      mAccount = email;
      mPassword = password;
      mMac = mac;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
      Log.i(TAG, "doInBackground(): ");
      // TODO: attempt authentication against a network service.
      try {
        // Simulate network access.
//        Thread.sleep(2000);
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("account", mAccount)
                .put("password", mPassword)
                .put("mac", mMac);
//        String url = String.format(ServerPath.FORMAT_LOGIN, mAccount, mPassword, mMac);
        RequestBody requestBody = new FormBody.Builder()
                .add("account", mAccount)
                .add("password", mPassword)
                .add("mac", mMac)
                .build();
//        RequestBody requestBody = RequestBody.create(mediaType, jsonRequest.toString());
        Request request = new Request.Builder()
                .url(ServerPath.LOGIN)
//                .url(url)
                .post(requestBody)
                .build();
        Log.d(TAG, "doInBackground(): request = " + request);
        Response response = okHttpClient.newCall(request).execute();
        Log.d(TAG, "doInBackground(): response = " + response);
        if ( !response.isSuccessful() ) {
          return null;
        }
        String result = response.body().string();
        Log.w(TAG, "doInBackground(): result = " + result);
        return new JSONObject(result);
      } catch ( IOException e ) {
        Log.d(TAG, "doInBackground(): ", e);
        return null;
      } catch ( JSONException e ) {
        Log.d(TAG, "doInBackground(): ", e);
        return null;
      }
    }

    @Override
    protected void onPostExecute(final JSONObject jsonObject) {
      loginAsyncTask = null;
      callback.onDisplayProgressBar(false);
      Log.d(TAG, "onPostExecute(): jsonObject = " + jsonObject);
      if ( jsonObject == null ) {
        callback.onDisplayLogin(false, R.string.error_server_disconnected);
      } else {
        try {
          int loginStatus = jsonObject.getInt(LoginStatus.KEY);
          long userId = jsonObject.getLong("userId");
          GlobalVariableTools.setUserId(activity, userId);
          checkLoginStatus(loginStatus);
          saveLoggedInUsers(mAccount, mPassword, loginStatus);
        } catch ( JSONException e ) {
          e.printStackTrace();
        }
      }
    }

    @Override
    protected void onCancelled() {
      Log.i(TAG, "onCancelled(): ");
      loginAsyncTask = null;
      callback.onDisplayLogin(false, R.string.cancel_login);
      callback.onDisplayProgressBar(false);
    }

    private void checkLoginStatus(int loginStatus) {
      int msgId;
      boolean loginEnabled = false;
      switch ( loginStatus ) {
        default:
          msgId = R.string.error_login_failed;
          break;
        case LoginStatus.SUCCESSFUL:
          msgId = R.string.msg_login_successful;
          loginEnabled = true;
          break;
        case LoginStatus.HAS_NOT_REGISTERED:
          msgId = R.string.error_has_not_registered;
          break;
        case LoginStatus.PHONE_ERROR:
          msgId = R.string.error_invalid_phone;
          callback.onDisplayAccountError(msgId);
          return;
        case LoginStatus.EMAIL_ERROR:
          msgId = R.string.error_invalid_email;
          callback.onDisplayAccountError(msgId);
          return;
        case LoginStatus.PASSWORD_ERROR:
          msgId = R.string.error_incorrect_password;
          callback.onDisplayPasswordError(msgId);
          return;
        case LoginStatus.DIFFERENT_MAC:
          //TODO: 不同设备登录
          msgId = R.string.warn_different_mac_address;
          loginEnabled = true;
          break;
        case LoginStatus.HAS_LOGGED_IN_ON_ANOTHER_PHONE:
          //TODO: 不同设备登录异常
          msgId = R.string.error_has_logged_in_on_another_phone;
          break;
        case LoginStatus.HAS_LOGGED_IN_ON_THE_PHONE:
          msgId = R.string.error_has_logged_in_on_the_phone;
          loginEnabled = true;
          break;
        case LoginStatus.FROZEN:
          msgId = R.string.error_account_frozen;
          break;
      }
      callback.onDisplayLogin(loginEnabled, msgId);
    }

    private void saveLoggedInUsers(String account, String password, int loginStatus) {
      if ( loginStatus == LoginStatus.SUCCESSFUL
              //// TODO: 2017/11/24
              || loginStatus == LoginStatus.DIFFERENT_MAC
              || loginStatus == LoginStatus.HAS_LOGGED_IN_ON_THE_PHONE ) {
        new LoggedInUsers()
                .setAccount(account)
                .setPassword(password)
                .saveOrUpdate("account = ?", account);
      }
    }
  }

}