package com.seckawijoki.graduation_project.functions.information_details;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/27 at 23:11.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class InformationDetailsActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_information_details);

    InformationDetailsFragment fragment = (InformationDetailsFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_information_details_fragment);
    if ( fragment == null ) {
      fragment = InformationDetailsFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_information_details_fragment
      );
    }

    new InformationDetailsPresenterImpl()
            .setView(fragment)
            .setModel(this)
            .initiate();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}