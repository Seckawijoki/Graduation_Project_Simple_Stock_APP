package com.seckawijoki.graduation_project.functions.browser;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 22:02.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class BrowserActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_browser);

    BrowserFragment fragment = (BrowserFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_browser_fragment);
    if ( fragment == null ) {
      fragment = BrowserFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_browser_fragment
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}