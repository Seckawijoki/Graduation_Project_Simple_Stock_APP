package com.seckawijoki.graduation_project.functions.register;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/24 at 15:35.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityIntent;
import com.seckawijoki.graduation_project.functions.login.LoginActivity;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;
import com.seckawijoki.graduation_project.util.ToastUtils;
import com.seckawijoki.graduation_project.util.ViewUtils;

public class RegisterFragment extends Fragment implements RegisterContract.View, View.OnClickListener {
  private ActionCallback callback;
  private Activity activity;
  private Button btnProgressFirst;
  private Button btnProgressSecond;
  private Button btnProgressThird;
  private ImageView imgBack;
  private AutoCompleteTextView actvPhone;
  private ImageView imgClearPhone;
  private AutoCompleteTextView actvVericode;
  private AutoCompleteTextView actvPassword;
  private ImageView imgClearPassword;
  private Button btnGetVericode;
  private Button btnRegister;
  private TextView tvLogin;
  private int sendVericodeCountDown = 10;
  private Handler sendVericodeHandler = new Handler();
  private Runnable runnable = new Runnable() {
    @Override
    public void run() {
      btnGetVericode.setEnabled(false);
      btnGetVericode.setText(
              String.format(
                      activity.getString(R.string.format_get_vericode_again),
                      sendVericodeCountDown));
      --sendVericodeCountDown;
//      activity.runOnUiThread(()->{
// });
      if (sendVericodeCountDown > 0)      {
        sendVericodeHandler.postDelayed(this, 1000);
      } else {
        sendVericodeCountDown = 10;
        btnGetVericode.setEnabled(true);
        btnGetVericode.setText(R.string.action_get_vericode);
      }
    }
  };
  public static RegisterFragment newInstance() {
    Bundle args = new Bundle();
    RegisterFragment fragment = new RegisterFragment();
    fragment.activity = fragment.getActivity();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_register, container, false);
    btnProgressFirst = view.findViewById(R.id.v_register_progress_first);
    btnProgressSecond = view.findViewById(R.id.v_register_progress_second);
    btnProgressThird = view.findViewById(R.id.v_register_progress_third);
    imgBack = view.findViewById(R.id.img_register_back);
    actvPhone = view.findViewById(R.id.et_register_phone);
    imgClearPhone = view.findViewById(R.id.img_clear_phone);
    actvVericode = view.findViewById(R.id.et_vericode);
    btnGetVericode = view.findViewById(R.id.btn_get_vericode);
    actvPassword = view.findViewById(R.id.et_register_password);
    imgClearPassword = view.findViewById(R.id.img_clear_register_password);
    btnRegister = view.findViewById(R.id.tv_register);
    tvLogin = view.findViewById(R.id.tv_go_back_to_login);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();
    ViewUtils.bindOnClick(this,
            imgBack, imgClearPhone, btnGetVericode, imgClearPassword, btnRegister, tvLogin);

//    actvPhone.setText("13510604840");
//    actvPhone.setText("13929762821");
//    actvPhone.setText("13929762821");
//    actvPhone.setText("15889655651");
    actvPhone.requestFocus();
//    actvPassword.setText("8823936");
//    actvVericode.requestFocus();
    actvPhone.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {      }
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {      }
      @Override public void afterTextChanged(Editable s) {
        if ( TextUtils.isEmpty(s.toString())){
          imgClearPhone.setVisibility(View.GONE);
          btnProgressFirst.setEnabled(false);
        } else {
          actvPhone.setError(null);
          imgClearPhone.setVisibility(View.VISIBLE);
          btnProgressFirst.setEnabled(true);
        }
      }
    });
    actvVericode.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {      }
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {      }
      @Override public void afterTextChanged(Editable s){
        String vericode = s.toString();
        if ( TextUtils.isEmpty(vericode) ) {
          btnProgressSecond.setEnabled(false);
        } else {
          actvVericode.setError(null);
          btnProgressSecond.setEnabled(true);
          if (vericode.length() == 4){
            callback.onRequestSubmitVericode(actvPhone.getText().toString(), vericode);
            ToastUtils.show(activity, R.string.msg_under_submitting_vericode);
          }
        }
      }
    });
    actvPassword.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {      }
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {      }
      @Override public void afterTextChanged(Editable s) {
        if ( TextUtils.isEmpty(s.toString())){
          btnProgressThird.setEnabled(false);
          imgClearPassword.setVisibility(View.GONE);
        } else {
          actvPassword.setError(null);
          btnProgressThird.setEnabled(true);
          imgClearPassword.setVisibility(View.VISIBLE);
        }
      }
    });
    new Handler().postDelayed(() -> {
//      btnGetVericode.performClick();
//      actvVericode.requestFocus();
    }, 500);
  }

  @Override
  public void onPresenterInitiate() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayGetVericode(boolean successful, int msgId) {
    activity.runOnUiThread(()-> {
      if (successful){
        actvVericode.requestFocus();
        btnProgressSecond.setEnabled(true);
      } else {
        actvPhone.setError(getString(msgId));
      }
      ToastUtils.show(activity, msgId);
    });
  }

  @Override
  public void displaySubmitVericode(boolean successful, int msgId) {
    displaySubmitVericode(successful, activity.getString(msgId));
  }

  @Override
  public void displaySubmitVericode(boolean successful, String msg) {
    activity.runOnUiThread(()-> {
      if (successful) {
        if (TextUtils.isEmpty(actvPassword.getText().toString())){
          actvPassword.requestFocus();
        } else {
          attemptToRegister();
        }
      } else {
        actvVericode.setError(getString(R.string.error_vericode_mismatched));
        actvVericode.requestFocus();
      }
      ToastUtils.show(activity, msg);
    });
  }

  @Override
  public void displayRegister(boolean successful, int msgId) {
    if (successful){
      btnProgressThird.setEnabled(true);
//      ( (MyApplication) activity.getApplicationContext() ).setLoginAccount(actvPhone.getText().toString());
      GlobalVariableUtils.setAccount(activity, actvPhone.getText().toString());
//      GlobalVariables.getInstance().account = actvPhone.getText().toString();
      startActivity(new Intent(ActivityIntent.MAIN));
      activity.finish();
    } else {
      actvVericode.setError(getString(msgId));
      ToastUtils.show(activity, msgId);
    }
  }

  private void attemptToRegister(){
    String vericode = actvVericode.getText().toString();
    if (TextUtils.isEmpty(vericode)){
      actvVericode.requestFocus();
      actvVericode.setError(getString(R.string.error_field_required));
      return;
    }
    String password = actvPassword.getText().toString();
    if (TextUtils.isEmpty(password)){
      actvPassword.requestFocus();
      actvPassword.setError(getString(R.string.error_field_required));
      return;
    }
    WifiManager wifiManager = (WifiManager)
            activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    String mac = wifiManager.getConnectionInfo().getMacAddress();
    callback.onRequestRegister(password, mac);
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {
      case R.id.img_register_back:
        activity.finish();
        break;
      case R.id.img_clear_phone:
        actvPhone.setText("");
        imgClearPhone.setVisibility(View.GONE);
        break;
      case R.id.btn_get_vericode:
        String phone = actvPhone.getText().toString();
        if (TextUtils.isEmpty(phone)){
          actvPhone.setError(getString(R.string.error_field_required));
          actvPhone.requestFocus();
          break;
        }
        sendVericodeHandler.post(runnable);
        btnGetVericode.setEnabled(false);
        callback.onRequestGetVericode("86", actvPhone.getText().toString());
        break;
      case R.id.img_clear_register_password:
        actvPassword.setText("");
        imgClearPassword.setVisibility(View.GONE);
        btnProgressThird.setEnabled(false);
        break;
      case R.id.tv_register:
        attemptToRegister();
        break;
      case R.id.tv_go_back_to_login:
        startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
        break;
    }
  }

}