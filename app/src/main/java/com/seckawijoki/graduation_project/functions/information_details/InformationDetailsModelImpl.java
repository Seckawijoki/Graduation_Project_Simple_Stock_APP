package com.seckawijoki.graduation_project.functions.information_details;

import android.util.Log;

import com.seckawijoki.graduation_project.db.client.Information;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/28 at 9:52.
 */

final class InformationDetailsModelImpl implements InformationDetailsContract.Model {
  private DataCallback callback;
  private static final String TAG = "InformationDetailsModel";

  @Override
  public void onViewInitiate() {

  }

  @Override
  public void destroy() {
    callback = null;
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestInformation(long informationId) {
    Information information;
    callback.onDisplayInformation(
            information = DataSupport
                    .where("informationId = ?", informationId + "")
                    .findFirst(Information.class)
    );
    Log.d(TAG, "requestInformation()\n: information = " + information);
  }
}