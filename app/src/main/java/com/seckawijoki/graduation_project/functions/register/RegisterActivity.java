package com.seckawijoki.graduation_project.functions.register;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/24 at 15:35.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.util.ActivityUtils;

public class RegisterActivity extends AppCompatActivity {
  private RegisterContract.Presenter presenter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    RegisterFragment fragment = (RegisterFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_register_fragment);
    if ( fragment == null ) {
      fragment = RegisterFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_register_fragment
      );
    }
    presenter = new RegisterPresenterImpl();
    RegisterContract.Model model = new RegisterModelImpl();
    presenter.setModel(model).setView(fragment).initiate();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.destroy();
  }



}