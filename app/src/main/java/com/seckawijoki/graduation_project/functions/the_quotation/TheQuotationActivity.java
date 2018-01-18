package com.seckawijoki.graduation_project.functions.the_quotation;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 22:58.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.util.ActivityUtils;

public class TheQuotationActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_the_quotation);

    TheQuotationFragment fragment = (TheQuotationFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_the_quotation_fragment);
    if ( fragment == null ) {
      fragment = TheQuotationFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_the_quotation_fragment
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}