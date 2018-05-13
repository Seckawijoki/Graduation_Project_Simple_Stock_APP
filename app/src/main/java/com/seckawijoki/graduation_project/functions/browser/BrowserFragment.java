package com.seckawijoki.graduation_project.functions.browser;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 22:02.
 */

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.utils.ToastUtils;

public class BrowserFragment extends Fragment {
  private Activity activity;
  private WebView wv;
  public static BrowserFragment newInstance() {
    Bundle args = new Bundle();
    BrowserFragment fragment = new BrowserFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_browser, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();
    View view = getView();
    Toolbar tb = view.findViewById(R.id.tb_browser);
    ( (AppCompatActivity) activity ).setSupportActionBar(tb);
    wv = view.findViewById(R.id.wv_browser);
    String url = activity.getIntent().getStringExtra(IntentKey.BROWSER_URL);
    wv.loadUrl(url);
    tb.setOnClickListener(v -> wv.loadUrl(url));
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_browser, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ){
      case android.R.id.home:
        activity.finish();
        break;
      case R.id.menu_message:
        startActivity(new Intent(IntentAction.MESSAGE));
        break;
      case R.id.menu_search:
        startActivity(new Intent(IntentAction.SEARCH));
        break;
      case R.id.menu_share:

        break;
      case R.id.menu_copy_website:
        ClipboardManager clipboardManager =
                (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        if ( clipboardManager != null ) {
          clipboardManager.setText(activity.getIntent().getStringExtra(IntentKey.BROWSER_URL));
          ToastUtils.show(activity, R.string.msg_succeed_in_copying);
        }
        break;
      case R.id.menu_open_in_browser:
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(activity.getIntent().getStringExtra(IntentKey.BROWSER_URL))
        );
        startActivity(intent);
        break;
      case R.id.menu_refresh:
        ( (WebView) getView().findViewById(R.id.wv_browser) )
                .loadUrl(activity.getIntent().getStringExtra(IntentKey.BROWSER_URL));
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

}