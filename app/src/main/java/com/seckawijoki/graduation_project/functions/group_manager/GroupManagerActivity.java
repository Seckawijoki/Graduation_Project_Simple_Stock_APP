package com.seckawijoki.graduation_project.functions.group_manager;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/13 at 16:12.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.util.ActivityUtils;

public class GroupManagerActivity extends AppCompatActivity {
  private static final String TAG = "GroupManagerActivity";
  private GroupManagerContract.Presenter presenter;
  private GroupManagerFragment fragment;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_manager);
    fragment = (GroupManagerFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_group_manager_fragment);
    if ( fragment == null ) {
      fragment = GroupManagerFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_group_manager_fragment
      );
    }
    presenter = new GroupManagerPresenterImpl()
            .setView(fragment)
            .setModel(new GroupManagerModelImpl(this))
            .setModel(this)
            .initiate();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.i(TAG, "onActivityResult(): ");
    switch ( resultCode ){
      case Activity.RESULT_OK:
        if (requestCode == ActivityRequestCode.GROUP_EDITOR ) {
          setIntent(data);
          ( (GroupManagerPresenterImpl) presenter )
                  .onRequestFavoriteGroups();
        }
        break;
      default:

        break;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}