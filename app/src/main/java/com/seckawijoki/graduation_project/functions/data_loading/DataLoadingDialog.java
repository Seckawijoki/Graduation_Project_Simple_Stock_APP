package com.seckawijoki.graduation_project.functions.data_loading;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.constants.server.StockType;
import com.seckawijoki.graduation_project.db.AccessibleChuangYeBan;
import com.seckawijoki.graduation_project.db.AccessibleStock;
import com.seckawijoki.graduation_project.db.server.FavoriteStock;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.tools.DbLogTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/11 at 11:17.
 */

public class DataLoadingDialog extends DialogFragment {
  private static final int DOWNLOAD_LIMIT = 400;
  private static final String TAG = "DataLoadingDialog";
  private OnDataLoadingListener listener;
  private TextView tvTip;
  private AsyncTask<Void, Integer, Boolean> asyncTask = new AsyncTask<Void, Integer, Boolean>() {

    @Override
    protected void onPreExecute() {
      setTips(R.string.msg_under_data_loading);
      super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
//      DataSupport.deleteAll(AccessibleSz.class);
//      DataSupport.deleteAll(AccessibleSh.class);
//      DataSupport.deleteAll(AccessibleChuangYeBan.class);
      DataSupport.deleteAll(FavoriteStock.class);
      DataSupport.deleteAll(Stock.class);
      try {
        setTips(R.string.msg_under_data_loading_sh);
        if ( DataSupport.count(AccessibleStock.class) < DOWNLOAD_LIMIT * 2) {
          DataSupport.deleteAll(AccessibleStock.class);
          downloadShIds();
        }
        if ( DataSupport.count(AccessibleChuangYeBan.class) <= 0 ) {
          setTips(R.string.msg_under_data_loading_chuang_ye_ban);
          downloadChuangYeBanIds();
        }
        return true;
      } catch ( IOException e ) {
        Log.e(TAG, "doInBackground(): ", e);
        setTips(R.string.error_failed_to_load_data);
      } catch ( JSONException e ) {
        Log.e(TAG, "doInBackground(): ", e);
        dismiss(R.string.error_failed_to_save_in_database);
      }
      return false;
    }

    @Override
    protected void onPostExecute(Boolean successful) {
      Log.i(TAG, "onPostExecute(): ");
      super.onPostExecute(successful);
      if ( successful ) {
        dismiss(R.string.msg_succeed_in_loading_data);
      } else {
//        dismiss(R.string.error_failed_to_load_data);
      }
      listener.onDataLoading(successful);
    }

    @Override
    protected void onCancelled() {
      Log.w(TAG, "onCancelled(): ");
      listener.onDataLoading(false);
      dismiss(R.string.error_internet_not_connected);
    }
  };

  public static DataLoadingDialog newInstance(OnDataLoadingListener listener) {
    DataLoadingDialog fragment;
    Bundle args = new Bundle();
    fragment = new DataLoadingDialog();
    fragment.setArguments(args);
    fragment.listener = listener;
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    View view = inflater.inflate(R.layout.dialog_data_loading, container, false);
    tvTip = view.findViewById(R.id.tv_data_loading_tips);
    return view;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    asyncTask.execute((Void) null);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    logDatabase();
    asyncTask.cancel(true);
    asyncTask = null;
  }

  private void setTips(final int resId) {
    getActivity().runOnUiThread(() -> tvTip.setText(resId));
  }

  public void dismiss(int resId) {
    setTips(resId);
    try {
      Thread.sleep(1000);
    } catch ( InterruptedException ignored ) {

    }
    dismissAllowingStateLoss();
  }

  private void downloadShIds() throws IOException, JSONException {
    Log.i(TAG, "downloadShIds(): ");
    RequestBody requestBody = new FormBody.Builder()
            .add("limit", String.valueOf(DOWNLOAD_LIMIT))
            .build();
    Request request = new Request.Builder()
            .url(ServerPath.GET_SH_IDS)
            .post(requestBody)
            .build();
    OkHttpClient okHttpClient = new OkHttpClient();
    Response response = okHttpClient.newCall(request).execute();
    String result = response.body().string();
    JSONArray jsonArray = new JSONArray(result);
    setTips(R.string.msg_under_saving_in_database);
    for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
      String stockId = jsonArray.getString(i);
      new AccessibleStock()
              .setStockId(stockId)
              .saveOrUpdate("stockId = ? and stockType = ?",
                      stockId, String.valueOf(StockType.SH));
    }
    setTips(R.string.msg_under_data_loading_sz);
    downloadSzIds();
  }

  private void downloadSzIds() throws IOException, JSONException {
    Log.i(TAG, "downloadSzIds(): ");
    RequestBody requestBody = new FormBody.Builder()
            .add("limit", String.valueOf(DOWNLOAD_LIMIT))
            .build();
    Request request = new Request.Builder()
            .url(ServerPath.GET_SZ_IDS)
            .post(requestBody)
            .build();
    OkHttpClient okHttpClient = new OkHttpClient();
    Response response = okHttpClient.newCall(request).execute();
    String result = response.body().string();
    JSONArray jsonArray = new JSONArray(result);
    setTips(R.string.msg_under_saving_in_database);
    for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
      String stockId = jsonArray.getString(i);
      new AccessibleStock()
              .setStockId(stockId)
              .saveOrUpdate("stockId = ? and stockType = ?",
                      stockId, String.valueOf(StockType.SZ));
    }
  }

  private void downloadChuangYeBanIds() throws IOException, JSONException {
    Log.i(TAG, "downloadChuangYeBanIds(): ");
    RequestBody requestBody = new FormBody.Builder()
            .add("limit", String.valueOf(DOWNLOAD_LIMIT))
            .build();
    Request request = new Request.Builder()
            .url(ServerPath.GET_CHUANG_YE_BAN_IDS)
            .post(requestBody)
            .build();
    OkHttpClient okHttpClient = new OkHttpClient();
    Response response = okHttpClient.newCall(request).execute();
    String result = response.body().string();
    JSONArray jsonArray = new JSONArray(result);
    setTips(R.string.msg_under_saving_in_database);
    for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
      String stockId = jsonArray.getString(i);
      new AccessibleChuangYeBan()
              .setStockId(stockId)
              .saveOrUpdate("stockId = " + stockId);
    }
  }

  private void logDatabase(){
    new Thread(() -> {
      DbLogTools.logAllDataSupport();
      DbLogTools.logCount();
      /*
      for ( int i = 0 ; i < list.size() ; i++ ) {
        AccessibleSz accessibleSinaSz = list.get(i);
        Log.d(TAG, "onDetach():  SinaSz" + i + "  = " + accessibleSinaSz);
      }
      */
    }).start();
  }

}
