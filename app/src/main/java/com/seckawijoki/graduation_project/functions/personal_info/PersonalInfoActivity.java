package com.seckawijoki.graduation_project.functions.personal_info;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/20 at 21:05.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class PersonalInfoActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_personal_info);

    PersonalInfoFragment fragment = (PersonalInfoFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_personal_info_fragment);
    if ( fragment == null ) {
      fragment = PersonalInfoFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_personal_info_fragment
      );
    }
    new PersonalInfoPresenterImpl()
            .setView(fragment)
            .setModel(this)
            .initiate();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}