package com.seckawijoki.graduation_project.functions.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityIntent;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.functions.login.LoginActivity;
import com.seckawijoki.graduation_project.functions.settings.OnLogoutListener;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;
import com.seckawijoki.graduation_project.util.ViewUtils;

import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class MineViewImpl implements MineContract.View, View.OnClickListener, Toolbar.OnMenuItemClickListener, OnLogoutListener {
  private static final String TAG = "MineViewImpl";
  private AppCompatActivity activity;
  private Fragment fragment;
  private View view;
  private ActionCallback callback;

  @Override
  public void initiate() {
    fragment.setHasOptionsMenu(true);
    Toolbar tb = view.findViewById(R.id.tb_mine);
    activity.setSupportActionBar(tb);
    tb.setOnMenuItemClickListener(this);
//    String account = ( (MyApplication) activity.getApplicationContext() ).getLoginAccount();
    String account = GlobalVariableUtils.getAccount(activity);
    Log.d(TAG, "initiate(): account = " + account);
    boolean showUserInfo = !TextUtils.isEmpty(account);
    displayShowUserInfo(showUserInfo);
    if (showUserInfo) {
      callback.onRequestUserInfo();
      callback.onRequestUserPortrait();
    }
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
  public void displayShowUserInfo(boolean show) {
    if ( show ) {
      view.findViewById(R.id.layout_has_logged_in).setVisibility(View.VISIBLE);
      view.findViewById(R.id.layout_has_not_logged_in).setVisibility(View.GONE);
    } else {
      view.findViewById(R.id.layout_has_logged_in).setVisibility(View.GONE);
      view.findViewById(R.id.layout_has_not_logged_in).setVisibility(View.VISIBLE);
    }
    if (show) {
      ViewUtils.bindOnClick(this,
              activity.findViewById(R.id.img_portrait),
              activity.findViewById(R.id.tv_nickname),
              activity.findViewById(R.id.tv_label_user_id),
              activity.findViewById(R.id.tv_user_id));
      activity.findViewById(R.id.tv_login).setOnClickListener(null);
      activity.findViewById(R.id.tv_register).setOnClickListener(null);
    } else {
      activity.findViewById(R.id.img_portrait).setOnClickListener(null);
      activity.findViewById(R.id.tv_nickname).setOnClickListener(null);
      activity.findViewById(R.id.tv_label_user_id).setOnClickListener(null);
      activity.findViewById(R.id.tv_user_id).setOnClickListener(null);
      ViewUtils.bindOnClick(this,
              activity.findViewById(R.id.tv_login),
              activity.findViewById(R.id.tv_register));
    }
  }

  @Override
  public void displayUserPortrait(File portraitFile) {
    activity.runOnUiThread(() -> {
      Bitmap bitmap = BitmapFactory.decodeFile(portraitFile.getPath());
      ( (ImageView) view.findViewById(R.id.img_portrait) ).setImageBitmap(bitmap);
    });
  }

  @Override
  public void displayUserInfo(String userId, String nickname) {
    ( (TextView) view.findViewById(R.id.tv_nickname) ).setText(nickname);
    ( (TextView) view.findViewById(R.id.tv_user_id) ).setText(userId);
  }

  private MineViewImpl() {
  }

  MineViewImpl(AppCompatActivity activity) {
    this.activity = activity;
  }

  MineViewImpl(Fragment fragment) {
    this.fragment = fragment;
    activity = (AppCompatActivity) fragment.getActivity();
    view = fragment.getView();
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch ( item.getItemId() ) {
      case R.id.menu_settings:
        fragment.startActivityForResult(new Intent(ActivityIntent.SETTINGS),
                ActivityRequestCode.LOGOUT);
        break;
      case R.id.menu_message:
        fragment.startActivity(new Intent(ActivityIntent.MESSAGE));
        break;
      default:
        return false;
    }
    return true;
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {
      case R.id.img_portrait:
      case R.id.tv_nickname:
      case R.id.tv_label_user_id:
      case R.id.tv_user_id:
        if ( TextUtils.isEmpty(GlobalVariableUtils.getUserId(activity)) )return;
        fragment.startActivityForResult(
                new Intent(ActivityIntent.PERSONAL_INFO),
                ActivityRequestCode.PERSONAL_INFO
        );
        break;
      case R.id.tv_login:
        if ( !TextUtils.isEmpty(GlobalVariableUtils.getUserId(activity)) ) return;
          GlobalVariableUtils.setAutoLogin(activity, false);
          fragment.startActivity(new Intent(activity, LoginActivity.class));

        break;
      case R.id.tv_register:
        if ( !TextUtils.isEmpty(GlobalVariableUtils.getUserId(activity)) ) return;
        activity.startActivity(new Intent(ActivityIntent.REGISTER));
        break;
    }
  }

  @Override
  public void onLogout() {
    activity.runOnUiThread(()->{
      displayShowUserInfo(false);
    });
  }
}