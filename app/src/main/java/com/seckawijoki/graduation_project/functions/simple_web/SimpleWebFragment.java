package com.seckawijoki.graduation_project.functions.simple_web;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/8 at 17:18.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.BundleKey;

public class SimpleWebFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnKeyListener {
  private static final String TAG = "SimpleWebFragment";
  private SwipeRefreshLayout layoutRefresh;
  private WebView wv;
  public static SimpleWebFragment newInstance() {
    return new SimpleWebFragment();
  }

  public  void reload(){
    wv.loadUrl(getArguments().getString(BundleKey.SIMPLE_WEB_URL));
  }

  public void goBack(){
    if (wv != null && wv.canGoBack())
      wv.goBack();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_simple_web, container, false);
  }

  @SuppressLint("SetJavaScriptEnabled")
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    layoutRefresh = getView().findViewById(R.id.layout_refresh_simple_web);
    layoutRefresh.setOnRefreshListener(this);
    wv = getView().findViewById(R.id.wv_simple_web);
    WebSettings webSettings = wv.getSettings();
    webSettings.setJavaScriptEnabled(true);
    wv.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.d(TAG, "shouldOverrideUrlLoading()\n: request.getUrl().getPath() = " + request.getUrl().getPath());
        view.loadUrl(request.getUrl().getPath());
        return true;
      }
    });
    wv.loadUrl(getArguments().getString(BundleKey.SIMPLE_WEB_URL));
    wv.setOnKeyListener(this);
  }


  @Override
  public void onRefresh() {
    layoutRefresh.setRefreshing(false);
    String url = wv.getUrl();
    if ( TextUtils.isEmpty(url) ){
      url = getArguments().getString(BundleKey.SIMPLE_WEB_URL);
    }
    wv.loadUrl(url);
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (v.getId() != R.id.wv_simple_web)return false;
    if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()){
      wv.goBack();
    }
    return true;
  }
}