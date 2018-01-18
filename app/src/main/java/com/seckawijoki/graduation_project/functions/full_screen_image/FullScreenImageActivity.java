package com.seckawijoki.graduation_project.functions.full_screen_image;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 16:33.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.util.ActivityUtils;

public class FullScreenImageActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_full_screen_image);

    FullScreenImageFragment fragment = (FullScreenImageFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_full_screen_image_fragment);
    if ( fragment == null ) {
      fragment = FullScreenImageFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_full_screen_image_fragment
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}