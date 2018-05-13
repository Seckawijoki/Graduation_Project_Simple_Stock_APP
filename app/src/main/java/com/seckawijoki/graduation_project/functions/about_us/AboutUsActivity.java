package com.seckawijoki.graduation_project.functions.about_us;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 17:54.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class AboutUsActivity extends AppCompatActivity {
  private AboutUsContract.Presenter presenter;
  private AboutUsFragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_us);

    fragment = (AboutUsFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_about_us_fragment);
    if ( fragment == null ) {
      fragment = AboutUsFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_about_us_fragment
      );
    }
    presenter = new AboutUsPresenterImpl()
            .setModel(this)
            .setView(fragment)
            .initiate();
  }
}