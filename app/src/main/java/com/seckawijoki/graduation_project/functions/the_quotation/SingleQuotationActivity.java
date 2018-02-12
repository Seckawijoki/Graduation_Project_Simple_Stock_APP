package com.seckawijoki.graduation_project.functions.the_quotation;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 22:58.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class SingleQuotationActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_quotation);

    SingleQuotationFragment fragment = (SingleQuotationFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_single_quotation_fragment);
    if ( fragment == null ) {
      fragment = SingleQuotationFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_single_quotation_fragment
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}