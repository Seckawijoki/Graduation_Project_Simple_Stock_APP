package com.seckawijoki.graduation_project.functions.TEST;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/25.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

import java.net.URISyntaxException;

public class TESTActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);

    TESTFragment fragment = (TESTFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_test_fragment);
    if (fragment == null){
      fragment = TESTFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_test_fragment
      );
    }
    TESTContract.Presenter presenter = new TESTPresenterImpl();
    TESTContract.Model model = null;
    try {
      model = new TESTModelImpl();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    presenter.setModel(model).setView(fragment).initiate();
  }
}