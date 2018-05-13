package com.seckawijoki.graduation_project.functions.latest_information;

import android.os.AsyncTask;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.server.FinndyApi;
import com.seckawijoki.graduation_project.constants.server.MoJiReTsu;
import com.seckawijoki.graduation_project.db.client.Information;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/4 at 16:30.
 */
class FinndyApiTask extends AsyncTask<Void, Void, Boolean> {
  private static final String TAG = "FinndyApiTask";
  private String token;

  public FinndyApiTask(String token) {
    this.token = token;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected void onPostExecute(Boolean aBoolean) {
    super.onPostExecute(aBoolean);

  }

  @Override
  protected void onProgressUpdate(Void... values) {
    super.onProgressUpdate(values);
  }

  @Override
  protected void onCancelled(Boolean aBoolean) {
    super.onCancelled(aBoolean);
  }

  @Override
  protected void onCancelled() {
    super.onCancelled();
  }

  @Override
  protected Boolean doInBackground(Void... voids) {
    JSONObject json = OkHttpUtils.post()
//            .addParam("pagesize", "10")
//            .addParam("pageindex", "0")
//            .addParam("datatype", "json")
//            .addParam("sortby", "desc")
            .addParam(MoJiReTsu.TOKEN, token)
            .url(FinndyApi.TOKEN_CRITIQUE)
            .execute()
            .jsonObject();
    try {
      JSONArray jsonArray = json.getJSONArray("datalist");
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      for ( int i = 0 ; i < jsonArray.length() ; ++i ) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Information information = new Information()
                .setInformationId(jsonObject.getLong("itemid"))
                .setTitle(jsonObject.getString("subject"))
                .setContent(jsonObject.getString("message"))
                .setDateTime(dateFormat.parse(jsonObject.getString("extfield41")))
                .setAuthor(jsonObject.getString("extfield42"))
                .setClassification(jsonObject.getString("extfield43"));
        Log.d(TAG, "doInBackground()\n: information = " + information);
        information.saveOrUpdate("informationId = ?", jsonObject.getString("itemid"));
      }
      return true;
    } catch ( JSONException e ) {
      e.printStackTrace();
    } catch ( ParseException e ) {
      e.printStackTrace();
    }
    return false;
  }
}
