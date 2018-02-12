package com.seckawijoki.graduation_project.functions.message;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 23:19.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class MessageActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_message);

    MessageFragment fragment = (MessageFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_message_fragment);
    if ( fragment == null ) {
      fragment = MessageFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_message_fragment
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}