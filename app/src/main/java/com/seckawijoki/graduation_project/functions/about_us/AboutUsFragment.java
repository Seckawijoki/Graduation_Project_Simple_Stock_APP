package com.seckawijoki.graduation_project.functions.about_us;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 17:54.
 */

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.DebuggingSwitcher;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.constants.common.ServiceAction;
import com.seckawijoki.graduation_project.functions.settings.SettingsDecoration;
import com.seckawijoki.graduation_project.functions.settings.SettingsFragment;
import com.seckawijoki.graduation_project.functions.settings.SettingsOptions;
import com.seckawijoki.graduation_project.utils.ToastUtils;
import com.seckawijoki.graduation_project.utils.ViewUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class AboutUsFragment
        extends Fragment
        implements AboutUsContract.View, View.OnClickListener, OnAboutUsOptionListener {
  private AboutUsAdapter adapter;
  private ActionCallback callback;
  private AlertDialog contactUsDialog;
  private Activity activity;
  private AlertDialog versionDialog;

  public static AboutUsFragment newInstance() {
    Bundle args = new Bundle();
    AboutUsFragment fragment = new AboutUsFragment();
    fragment.activity = fragment.getActivity();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_about_us, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();
    View view = getView();
    Toolbar tb = view.findViewById(R.id.tb_about_us);
    ( (AppCompatActivity) activity ).setSupportActionBar(tb);
    try {
      PackageInfo packageInfo = activity.getPackageManager()
              .getPackageInfo(activity.getPackageName(), 0);
      String versionName = packageInfo.versionName;
      ( (TextView) view.findViewById(R.id.tv_app_name_and_version) ).setText(
              String.format(activity.getString(R.string.format_app_name_and_version),
                      activity.getString(R.string.app_name),
                      versionName
              )
      );
    } catch ( PackageManager.NameNotFoundException e ) {
      ( (TextView) view.findViewById(R.id.tv_app_name_and_version) ).setText(activity.getString(R.string.app_name));
    }
    RecyclerView rv = view.findViewById(R.id.rv_about_us);
    rv.setLayoutManager(new LinearLayoutManager(activity));
    rv.addItemDecoration(new SettingsDecoration(activity));
    rv.setAdapter(adapter = new AboutUsAdapter(activity)
            .setOnAboutUsOptionListener(this));
    view.findViewById(R.id.img_app_icon).setOnClickListener(this);
    view.findViewById(R.id.tv_app_name_and_version).setOnClickListener(this);
    view.findViewById(R.id.tv_official_website).setOnClickListener(this);
    callback.onRequestLatestAppVersionCode();
    autoDebuggingEvent();
  }

  private void autoDebuggingEvent() {
    if ( DebuggingSwitcher.APP_UPDATE ) {
      new Handler().postDelayed(this::downloadApk,
              300);
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_about_us, menu);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case android.R.id.home:
        getActivity().finish();
        break;
      case R.id.menu_message:
        startActivity(new Intent(IntentAction.MESSAGE));
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onPresenterInitiate() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayLatestAppVersionCode(int versionCode) {
    adapter.setLatestVersionCode(versionCode).notifyDataSetChanged();
  }

  @Override
  public void displayLatestAppVersion(int versionCode, String versionName, String versionDescription) {
    View view = activity.getLayoutInflater().inflate(R.layout.alert_dialog_version_update, null);
    view.findViewById(R.id.btn_put_aside).setOnClickListener(this);
    view.findViewById(R.id.btn_update_right_now).setOnClickListener(this);
    TextView tv = view.findViewById(R.id.tv_version_description);
    tv.setText(versionDescription);
    versionDialog = new AlertDialog.Builder(activity)
            .setTitle(String.format(
                    activity.getString(R.string.format_find_latest_version),
                    versionName
            ))
            .setView(view)
            .create();
    versionDialog.show();
  }

  @Override
  public void onClick(View v) {
    String url;
    switch ( v.getId() ) {
      case R.id.img_app_icon:
        url = activity.getString(R.string.action_official_website);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        break;
      case R.id.tv_app_name_and_version:
        downloadApk();
        break;
      case R.id.tv_dial_author_phone_number:
        contactUsDialog.dismiss();
        url = String.format(activity.getString(R.string.format_dial),
                activity.getString(R.string.phone_author)
        );
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
        break;
      case R.id.tv_qq_chat_with_author:
        contactUsDialog.dismiss();
        url = String.format(activity.getString(R.string.format_url_jump_to_qq),
                activity.getString(R.string.qq_author)
        );
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        break;
      case R.id.tv_official_website:
        url = activity.getString(R.string.action_official_website);
        Intent intent = new Intent(IntentAction.BROWSER);
        intent.putExtra(IntentKey.BROWSER_URL, url);
        startActivity(intent);
        break;
      case R.id.btn_put_aside:
        versionDialog.dismiss();
        break;
      case R.id.btn_update_right_now:
        versionDialog.dismiss();
        downloadApk();
        break;
    }
  }

  @Override
  public void onAboutUsOptionClick(int position) {
    switch ( position ) {
      case AboutUsOptions.VERSION_UPDATE:
        ToastUtils.show(activity, R.string.msg_under_checking_update);
        callback.onRequestLatestAppVersion();
        break;
      case AboutUsOptions.GRADE_MY_APP:
        ToastUtils.show(activity, R.string.msg_has_not_appeared_on_the_market);
        break;
      case AboutUsOptions.DISCLAIMER_AND_PRIVACY_STATEMENT:
        startActivity(new Intent(IntentAction.DISCLAIMER));
        break;
      case AboutUsOptions.CONTACT_US:
        contactUs();
        break;
    }
  }

  private void downloadApk() {
    ToastUtils.show(activity, "Debugging: 开始下载最新Apk");
    activity.startService(new Intent(activity, AppUpgradeService.class));
//    callback.onRequestLatestApk();
//    Notification.Builder builder = new Notification.Builder(activity);
  }

  private void contactUs() {
    View view = LayoutInflater.from(activity).inflate(R.layout.alert_dialog_contact_us, null);
    ViewUtils.bindOnClick(this,
            view.findViewById(R.id.tv_dial_author_phone_number),
            view.findViewById(R.id.tv_qq_chat_with_author)
    );
    contactUsDialog = new AlertDialog.Builder(activity)
            .setTitle(R.string.label_please_select)
            .setView(view)
            .create();
    contactUsDialog.show();

  }
}