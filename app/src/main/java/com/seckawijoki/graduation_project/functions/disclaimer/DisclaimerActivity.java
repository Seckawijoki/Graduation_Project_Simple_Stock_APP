package com.seckawijoki.graduation_project.functions.disclaimer;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/13 at 14:26.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class DisclaimerActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_disclaimer);

    DisclaimerFragment fragment = (DisclaimerFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_disclaimer_fragment);
    if ( fragment == null ) {
      fragment = DisclaimerFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_disclaimer_fragment
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}