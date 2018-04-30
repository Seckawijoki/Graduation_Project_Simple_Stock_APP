package com.seckawijoki.graduation_project.functions.login;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/12 at 15:38.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.FragmentTag;
import com.seckawijoki.graduation_project.functions.data_loading.DataLoadingDialog;
import com.seckawijoki.graduation_project.functions.data_loading.OnDataLoadingListener;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.ToastUtils;
import com.seckawijoki.graduation_project.utils.ViewUtils;

import java.util.List;

public class LoginFragment extends Fragment
        implements LoginContract.View, View.OnClickListener, OnDataLoadingListener{
  private static final String TAG = "LoginFragment";
  private ActionCallback callback;
  private Activity activity;
  private ScrollView sv;
  private ImageView imgBack;
  private AutoCompleteTextView actvAccount;
  private ImageView imgClearAccount;
  private AutoCompleteTextView actvPassword;
  private ImageView imgClearPassword;
  private CheckBox chbShowPassword;
  private Button btnSignIn;
  private TextView tvVisitorLogin;
  private TextView tvForgetPassword;
  private TextView tvRegister;
  private TextView tvTestActivity;
  private ProgressBar pb;
  private String mac;
  public static LoginFragment newInstance() {
    Bundle args = new Bundle();
    LoginFragment fragment = new LoginFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_login, container, false);
    sv = view.findViewById(R.id.sv_login);
    imgBack = view.findViewById(R.id.img_login_back);
    actvAccount = view.findViewById(R.id.actv_login_phone_or_email);
    imgClearAccount = view.findViewById(R.id.img_clear_account);
    actvPassword = view.findViewById(R.id.actv_login_password);
    imgClearPassword = view.findViewById(R.id.img_clear_login_password);
    chbShowPassword = view.findViewById(R.id.chb_show_password);
    btnSignIn = view.findViewById(R.id.btn_sign_in);
    tvVisitorLogin = view.findViewById(R.id.tv_visitor_login);
    tvForgetPassword = view.findViewById(R.id.tv_forget_password);
    tvRegister = view.findViewById(R.id.tv_register);
    tvTestActivity = view.findViewById(R.id.tv_test_activity);
    pb = view.findViewById(R.id.pb_login);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();
    WifiManager wifiManager = (WifiManager)
            activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    mac = wifiManager.getConnectionInfo().getMacAddress();
    ViewUtils.bindOnClick(this,
            imgBack, imgClearAccount, imgClearPassword, chbShowPassword,
            btnSignIn, tvVisitorLogin, tvForgetPassword, tvRegister, tvTestActivity);
    //TODO: debugging test
//    actvAccount.setText("135");
//    actvAccount.setText("1");
//    actvAccount.setText("13510604840");
//    actvPassword.setText("8823936");
    actvAccount.requestFocus();
//    actvAccount.showDropDown();
//    actvPassword.requestFocus();
    actvAccount.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
      @Override public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())){
          imgClearAccount.setVisibility(View.GONE);
        } else {
          imgClearAccount.setVisibility(View.VISIBLE);
        }
      }
    });
    actvPassword.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
      @Override public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())){
          imgClearPassword.setVisibility(View.GONE);
        } else {
          imgClearPassword.setVisibility(View.VISIBLE);
        }
      }
    });
    callback.onRequestAccountList();
    callback.onRequestAutoLogin(mac);
    new Handler().postDelayed(() -> {
//      tvVisitorLogin.performClick();
//      btnSignIn.performClick();
//      tvRegister.performClick();
    }, 500);
    /*
    actvAccount.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
      @Override
      public void afterTextChanged(Editable s) {
        account = s.toString();
      }
    });
    actvPassword.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
      @Override
      public void afterTextChanged(Editable s) {
        password = s.toString();
      }
    });
    */
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
  public void displayLogin(boolean successful, int stringId) {
    ToastUtils.show(activity, stringId);
    Log.d(TAG, "displayLogin(): successful = " + successful);
    if (successful){
//      GlobalVariables.newInstance().account = account;
//      ( (MyApplication) activity.getApplicationContext() ).setLoginAccount(account);
      GlobalVariableTools.setAccount(activity, actvAccount.getText().toString());
      /*
      activity.runOnUiThread(()->{
        DataLoadingDialog dataLoadingDialog = DataLoadingDialog.newInstance(LoginFragment.this);
        dataLoadingDialog.show(getFragmentManager(), FragmentTag.DATA_LOADING);
      });
*/
      DataLoadingDialog dataLoadingDialog = DataLoadingDialog.newInstance(LoginFragment.this);
      dataLoadingDialog.show(getFragmentManager(), FragmentTag.DATA_LOADING);
      GlobalVariableTools.setAutoLogin(activity, true);
    }
  }

  @Override
  public void displayAutoLogin(String account, String password) {
    actvAccount.setText(account);
    actvPassword.setText(password);
    actvPassword.setSelection(password.length());
    actvPassword.requestFocus();
    if ( GlobalVariableTools.getAutoLogin(activity)){
      callback.onRequestLogin(account, password, mac);
    }
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {
      case R.id.img_login_back:
        activity.finish();
        break;
      case R.id.img_clear_account:
        actvAccount.setText("");
        imgClearAccount.setVisibility(View.GONE);
        break;
      case R.id.img_clear_login_password:
        Log.d(TAG, "onClick()\n: v.getId() == R.id.img_clear_login_password = "
                + (v.getId() == R.id.img_clear_login_password));
        actvPassword = activity.findViewById(R.id.actv_login_password);
        actvPassword.setText("");
        imgClearPassword.setVisibility(View.GONE);
        break;
      case R.id.chb_show_password:
        String password = actvPassword.getText().toString();
        if ( chbShowPassword.isChecked() ) {
          actvPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
          actvPassword.setInputType(
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                  | InputType.TYPE_CLASS_TEXT);
        }
        actvPassword.setText(password);
        break;
      case R.id.btn_sign_in:
        attemptLogin();
        break;
      case R.id.tv_visitor_login:
        startActivity(new Intent(IntentAction.VISITOR));
        break;
      case R.id.tv_forget_password:
        ToastUtils.show(activity, "TODO");
        break;
      case R.id.tv_register:
        startActivity(new Intent(IntentAction.REGISTER));
        break;
      case R.id.tv_test_activity:
        startActivity(new Intent(IntentAction.TEST));
        break;
      default:

        break;
    }
  }

  @Override
  public void onDataLoading(boolean finish) {
    if (finish){
      startActivity(new Intent(IntentAction.MAIN));
      activity.finish();
    } else {
      ToastUtils.show(activity, "数据加载失败");
    }
  }

  /**
   * Attempts to sign in or register the account specified by the login form.
   * If there are form errors (invalid email, missing fields, etc.), the
   * errors are presented and no actual login attempt is made.
   */
  private void attemptLogin() {
//    if (mAuthTask != null)       return;

    // Reset errors.
    actvAccount.setError(null);
    actvPassword.setError(null);

    // Store values at the time of the login attempt.
    String account = actvAccount.getText().toString();
    String password = actvPassword.getText().toString();

    boolean cancel = false;
    View focusView = null;

    // Check for a valid password, if the user entered one.
    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      actvPassword.setError(getString(R.string.error_too_short_password));
      focusView = actvPassword;
      cancel = true;
    }

    // Check for a valid email address.
    if (TextUtils.isEmpty(account)) {
      actvAccount.setError(getString(R.string.error_field_required));
      focusView = actvAccount;
      cancel = true;
    } else if (!isAccountValid(account)) {

      actvAccount.setError(getString(R.string.error_invalid_email));
      focusView = actvAccount;
      cancel = true;
    }

    if (cancel) {
      // There was an error; don't attempt login and focus the first
      // form field with an error.
      focusView.requestFocus();
    } else {
      // Show a progress spinner, and kick off a background task to
      // perform the user login attempt.
      displayProgressBar(true);
//      mAuthTask = new LoginAsyncTask(account, password);
//      mAuthTask.execute((Void) null);
      callback.onRequestLogin(account, password, mac);
      Log.i(TAG, "attemptLogin(): ");
    }
  }

  private boolean isAccountValid(String account) {
    //TODO: Replace this with your own logic
//    return email.contains("@");
    return true;
  }

  private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 6;
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  @Override
  public void displayProgressBar(final boolean show) {
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.
    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = activity.getResources().getInteger(android.R.integer.config_shortAnimTime);
/*
      sv.setVisibility(show ? View.GONE : View.VISIBLE);
      sv.animate().setDuration(shortAnimTime).alpha(
              show ? 0 : 1).setOnQuotationClickListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          sv.setVisibility(show ? View.GONE : View.VISIBLE);
        }
      });
      */
      pb.setVisibility(show ? View.VISIBLE : View.GONE);
      pb.animate().setDuration(shortAnimTime).alpha(
              show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          pb.setVisibility(show ? View.VISIBLE : View.GONE);
        }
      });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply show
      // and hide the relevant UI components.
      pb.setVisibility(show ? View.VISIBLE : View.GONE);
      sv.setVisibility(show ? View.GONE : View.VISIBLE);
    }
  }

  @Override
  public void displayAccountError(int stringId) {
    actvAccount.setError(getString(stringId));
    actvAccount.requestFocus();
  }

  @Override
  public void displayPasswordError(int stringId) {
    actvPassword.setError(getString(stringId));
    actvPassword.requestFocus();
  }

  @Override
  public void displayAccountList(List<String> accountList) {
//    ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, R.activity_register.list_item_account, accountList);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, accountList);
    actvAccount.setAdapter(adapter);
  }
}