package com.seckawijoki.graduation_project.functions.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityIntent;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;
import com.seckawijoki.graduation_project.util.ViewUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/27 at 21:10.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener{
  private static final String TAG = "SettingsFragment";
  private AppCompatActivity activity;
  private ViewGroup layoutMultiAccount;
  private ViewGroup layoutAccountAndSafety;
  private ViewGroup layoutAboutUs;
  private ViewGroup layoutLogout;
  private Toolbar tb;
  public static SettingsFragment newInstance() {
    Bundle args = new Bundle();
    SettingsFragment fragment = new SettingsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_settings, container, false);
    layoutMultiAccount = view.findViewById(R.id.layout_multi_account_management);
    layoutAccountAndSafety = view.findViewById(R.id.layout_account_and_safety);
    layoutAboutUs = view.findViewById(R.id.layout_about_us);
    layoutLogout = view.findViewById(R.id.layout_logout);
    setHasOptionsMenu(true);
    tb = view.findViewById(R.id.tb_settings);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = ( (AppCompatActivity) getActivity() );
    activity.setSupportActionBar(tb);
//    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    ViewUtils.bindOnClick(this,
            layoutMultiAccount,
            layoutAccountAndSafety,
            layoutAboutUs,
            layoutLogout);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_settings, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d(TAG, "onOptionsItemSelected(): item.getItemId() = " + item.getItemId());
    switch ( item.getItemId() ){
      case R.id.menu_message:
        Log.d(TAG, "onOptionsItemSelected(): R.id.menu_message = " + R.id.menu_message);
        startActivity(new Intent(ActivityIntent.MESSAGE));
        break;
      case android.R.id.home:
        Log.d(TAG, "onOptionsItemSelected(): android.R.id.home = " + android.R.id.home);
        activity.setResult(Activity.RESULT_CANCELED);
        activity.finish();
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  private void attemptToLogout(){
//    account = GlobalVariables.getInstance().account;
//    account = ( (MyApplication) getActivity().getApplicationContext() ).getLoginAccount();
    final String account = GlobalVariableUtils.getAccount(activity);
    Log.d(TAG, "attemptToLogout(): account = " + account);
    new Thread(() -> {
      Log.i(TAG, "attemptToLogout(): ");
      if ( TextUtils.isEmpty(account) ) return;
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("account", account)
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.LOGOUT)
              .post(requestBody)
              .build();
      try {
        Response response = okHttpClient.newCall(request).execute();
        if ( response.isSuccessful() ) {
          Log.d(TAG, "attemptToLogout(): response.body().string() = " + response.body().string());
//          GlobalVariables.getInstance().account = null;
//          ( (MyApplication) getActivity().getApplicationContext() ).setLoginAccount(null);
//          GlobalVariableUtils.setAccount(getActivity(), null);
          Intent data = new Intent();
          data.putExtra(IntentKey.HAS_LOGGED_OUT, true);
          activity.setResult(Activity.RESULT_OK, data);
          activity.finish();
        }
      } catch ( IOException e ) {
        e.printStackTrace();
      }
    }).start();
  }

  @Override
  public void onClick(View v) {
    Log.d(TAG, "onClick(): v.getId() = " + v.getId());
    // TODO: 2017/11/30
    switch ( v.getId() ){
      case R.id.layout_multi_account_management:

        break;
      case R.id.layout_account_and_safety:

        break;
      case R.id.layout_about_us:

        break;
      case R.id.layout_logout:
        attemptToLogout();
        break;
    }
  }

}
