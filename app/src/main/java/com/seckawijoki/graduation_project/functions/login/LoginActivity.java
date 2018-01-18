package com.seckawijoki.graduation_project.functions.login;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/12 at 15:39.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.functions.main.MainActivity;
import com.seckawijoki.graduation_project.util.ActivityUtils;
import com.seckawijoki.graduation_project.util.ToastUtils;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    LoginFragment fragment = (LoginFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_login_fragment);
    if ( fragment == null ) {
      fragment = LoginFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_login_fragment
      );
    }
    LoginContract.Presenter presenter = new LoginPresenterImpl();
    LoginContract.Model model = new LoginModelImpl(this);
    presenter.setModel(model).setView(fragment).initiate();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

}