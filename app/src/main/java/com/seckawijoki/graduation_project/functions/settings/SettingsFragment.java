package com.seckawijoki.graduation_project.functions.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.seckawijoki.graduation_project.constants.app.DebuggingSwitcher;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/27 at 21:10.
 */

public class SettingsFragment extends Fragment implements OnSettingsOptionListener {
  private static final String TAG = "SettingsFragment";
  private SettingsAdapter adapter;
  private AppCompatActivity activity;

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
    setHasOptionsMenu(true);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = ( (AppCompatActivity) getActivity() );
    View view = getView();
    Toolbar tb = view.findViewById(R.id.tb_settings);
    activity.setSupportActionBar(tb);
    RecyclerView rv = view.findViewById(R.id.rv_settings);
    rv.setLayoutManager(new LinearLayoutManager(activity));
    rv.addItemDecoration(new SettingsDecoration(activity));
    rv.setAdapter(adapter = new SettingsAdapter(activity)
            .setOnSettingsOptionListener(this));
    autoDebuggingEvent();
  }

  private void autoDebuggingEvent(){
    if ( DebuggingSwitcher.APP_UPDATE) {
      new Handler().postDelayed(() ->
                      SettingsFragment.this.onSettingsOptionClick(SettingsOptions.ABOUT_US),
              300);
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_settings, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case R.id.menu_message:
        startActivity(new Intent(IntentAction.MESSAGE));
        break;
      case android.R.id.home:
        activity.setResult(Activity.RESULT_CANCELED);
        activity.finish();
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  private void logout() {
//    account = GlobalVariables.newInstance().account;
//    account = ( (MyApplication) getActivity().getApplicationContext() ).getLoginAccount();
    final String account = GlobalVariableTools.getAccount(activity);
    Log.d(TAG, "logout(): account = " + account);
    new Thread(() -> {
      Log.i(TAG, "logout(): ");
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
          Log.d(TAG, "logout(): response.body().string() = " + response.body().string());
//          GlobalVariables.newInstance().account = null;
//          ( (MyApplication) getActivity().getApplicationContext() ).setLoginAccount(null);
//          GlobalVariableTools.setAccount(getActivity(), null);
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
  public void onSettingsOptionClick(int position) {
    switch ( position ) {
      case SettingsOptions.MULTI_ACCOUNT_MANAGEMENT:

        break;
      case SettingsOptions.ACCOUNT_AND_SAFETY:

        break;
      case SettingsOptions.PRIVACY:

        break;
      case SettingsOptions.COMMON_SETTINGS:

        break;
      case SettingsOptions.ABOUT_US:
        startActivity(new Intent(IntentAction.ABOUT_US));
        break;
      case SettingsOptions.LOGOUT:
        logout();
        break;
    }
  }
}
