package com.seckawijoki.graduation_project.functions.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/2 at 23:12.
 */

public class SettingsActivity extends AppCompatActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    SettingsFragment fragment = (SettingsFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_settings_fragment);
    if ( fragment == null ) {
      fragment = SettingsFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_settings_fragment
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}
