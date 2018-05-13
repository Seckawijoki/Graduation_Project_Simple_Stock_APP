package com.seckawijoki.graduation_project.functions.information_website;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/13 at 14:36.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class InformationWebsitesActivity extends AppCompatActivity {
  private InformationWebsitesContract.Presenter presenter;
  private InformationWebsitesFragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_information_websites);

    fragment = (InformationWebsitesFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_information_websites_fragment);
    if ( fragment == null ) {
      fragment = InformationWebsitesFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_information_websites_fragment
      );
    }
    presenter = new InformationWebsitesPresenterImpl()
            .setModel(this)
            .setView(fragment)
            .initiate();
  }
}