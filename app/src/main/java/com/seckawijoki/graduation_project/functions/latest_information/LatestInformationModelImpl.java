package com.seckawijoki.graduation_project.functions.latest_information;

import com.seckawijoki.graduation_project.constants.server.FinndyApi;
import com.seckawijoki.graduation_project.db.client.Information;
import com.seckawijoki.graduation_project.db.client.SimpleInformation;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class LatestInformationModelImpl implements LatestInformationContract.Model {
  private DataCallback callback;
  private FinndyApiTask finndyApiTask;

  @Override
  public void onPresenterInitiate() {

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
  public void requestLatestInformation() {
    if ( finndyApiTask != null ) return;
    finndyApiTask = new FinndyApiTask(FinndyApi.TOKEN_CRITIQUE);
    finndyApiTask.execute();
    try {
      if ( finndyApiTask.get() ) {
        requestLatestInformationFromDatabase();
      }
    } catch ( InterruptedException | ExecutionException e ) {
      e.printStackTrace();
    }
    finndyApiTask = null;
  }

  private void requestLatestInformationFromDatabase() {
    List<Information> informationList = DataSupport
            .select("informationId", "title", "dateTime", "imageUri")
//            .order("dateTime desc")
            .find(Information.class);
    List<SimpleInformation> simpleInformationList = new ArrayList<>(informationList.size());
    for ( int i = 0 ; i < informationList.size() ; i++ ) {
      Information information = informationList.get(i);
      simpleInformationList.add(
              new SimpleInformation()
                      .setInformationId(information.getInformationId())
                      .setTitle(information.getTitle())
                      .setImageUri(information.getImageUri())
                      .setDateTime(information.getDateTime())
      );
    }
    callback.onDisplayLatestInformation(simpleInformationList);
  }

}