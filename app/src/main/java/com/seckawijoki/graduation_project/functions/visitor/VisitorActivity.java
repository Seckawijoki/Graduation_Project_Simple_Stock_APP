package com.seckawijoki.graduation_project.functions.visitor;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/23 at 22:39.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class VisitorActivity extends AppCompatActivity {
  private VisitorContract.Presenter presenter;
  private VisitorFragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_visitor);

    fragment = (VisitorFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_visitor_fragment);
    if ( fragment == null ) {
      fragment = VisitorFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_visitor_fragment
      );
    }
    presenter = new VisitorPresenterImpl()
            .setModel(this)
            .setView(fragment)
            .initiate();
  }
}