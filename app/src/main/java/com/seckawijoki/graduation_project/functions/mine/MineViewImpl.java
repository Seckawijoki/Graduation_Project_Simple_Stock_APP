package com.seckawijoki.graduation_project.functions.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.DebuggingSwitcher;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.ServiceAction;
import com.seckawijoki.graduation_project.functions.login.LoginActivity;
import com.seckawijoki.graduation_project.functions.settings.OnLogoutListener;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.ViewUtils;

import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class MineViewImpl implements MineContract.View,
        View.OnClickListener,
        Toolbar.OnMenuItemClickListener,
        OnLogoutListener{
  private static final String TAG = "MineViewImpl";
  private AppCompatActivity activity;
  private Fragment fragment;
  private View view;
  private ActionCallback callback;

  @Override
  public void initiate() {
    Toolbar tb = view.findViewById(R.id.tb_mine);
    activity.setSupportActionBar(tb);
    MenuItem menuItem = view.findViewById(R.id.menu_settings);
    tb.setOnMenuItemClickListener(this);
//    String account = ( (MyApplication) activity.getApplicationContext() ).getLoginAccount();
    String userId = GlobalVariableTools.getUserId(activity);
    boolean showUserInfo = !TextUtils.isEmpty(userId);
    displayShowUserInfo(showUserInfo);
    if (showUserInfo) {
      callback.onRequestUserInfo();
    }
    // TODO: 2018/2/11 DEBUGGING AUTO-CLICK
    autoDebuggingEvent();

  }

  private void autoDebuggingEvent(){
    if ( DebuggingSwitcher.APP_UPDATE){
      new Handler().postDelayed(() ->
        fragment.startActivityForResult(new Intent(IntentAction.SETTINGS),
                ActivityRequestCode.SETTINGS),
              1000);
    }
  }

  @Override
  public void destroy() {
    activity = null;
    fragment = null;
    callback = null;
  }

  private void onRequestUserPortrait(){
//    callback.onRequestUserPortrait();
    Intent intent = new Intent(ServiceAction.PORTRAIT_DOWNLOAD);
    activity.startService(intent);

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
    callback.onRequestUserPortrait();
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
        fragment.startActivityForResult(new Intent(IntentAction.SETTINGS),
                ActivityRequestCode.SETTINGS);
        break;
      case R.id.menu_message:
        fragment.startActivity(new Intent(IntentAction.MESSAGE));
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
        if ( TextUtils.isEmpty(GlobalVariableTools.getUserId(activity)) )return;
        fragment.startActivityForResult(
                new Intent(IntentAction.PERSONAL_INFO),
                ActivityRequestCode.PERSONAL_INFO
        );
        break;
      case R.id.tv_login:
        if ( !TextUtils.isEmpty(GlobalVariableTools.getUserId(activity)) ) return;
          GlobalVariableTools.setAutoLogin(activity, false);
          fragment.startActivity(new Intent(activity, LoginActivity.class));

        break;
      case R.id.tv_register:
        if ( !TextUtils.isEmpty(GlobalVariableTools.getUserId(activity)) ) return;
        activity.startActivity(new Intent(IntentAction.REGISTER));
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