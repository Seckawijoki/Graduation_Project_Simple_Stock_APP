package com.seckawijoki.graduation_project.functions.register;

import android.app.Activity;
import android.util.Log;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.server.ServerPath;
import com.seckawijoki.graduation_project.db.client.LoggedInUsers;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/24 at 15:32.
 */

class RegisterModelImpl extends EventHandler implements RegisterContract.Model {
  private static final String TAG = "RegisterModelImpl";
  private DataCallback callback;
  private ExecutorService pool = Executors.newFixedThreadPool(2);
  private String phone;
  private Activity context;
  RegisterModelImpl(Activity context) {
    this.context = context;
  }

  @Override
  public void onViewInitiate() {
    initiateModSDK();
  }

  @Override
  public void destroy() {
    callback = null;
    pool.shutdownNow();
    SMSSDK.unregisterEventHandler(this);
  }

  @Override
  public void setDataCallback(DataCallback callback) {
    this.callback = callback;
  }

  @Override
  public void requestGetVericode(String country, String phone) {
    this.phone = phone;
    Log.d(TAG, "requestGetVericode(): phone = " + phone);
    Callable<Boolean> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("phone", phone)
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.CHECK_PHONE_EXISTENT)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
      if ( response.isSuccessful() ) {
        Boolean existent = Boolean.valueOf(response.body().string());
        Log.d(TAG, "requestGetVericode(): existent = " + existent);
        return existent;
      } else {
        return false;
      }
    };
    Future<Boolean> future = pool.submit(callable);
    try {
      Boolean existent = future.get();
      if ( !existent ) {
        SMSSDK.getVerificationCode(country, phone, null);
      } else {
        callback.onDisplayGetVericode(false, R.string.error_phone_been_registered);
      }
    } catch ( InterruptedException | ExecutionException e ) {
      Log.e(TAG, "requestGetVericode(): ", e);
    }
  }

  @Override
  public void requestSubmitVericode(String phone, String vericode) {
    if ( vericode.length() != 4 ) return;
    this.phone = phone;
    Log.d(TAG, "requestSubmitVericode(): vericode = " + vericode);
    SMSSDK.submitVerificationCode("86", phone, vericode);
  }

  @Override
  public void requestRegister(String phone, final String password, final String mac) {
    Log.d(TAG, "requestRegister(): phone = " + (this.phone = phone));
    Log.d(TAG, "requestRegister(): password = " + password);
    Log.d(TAG, "requestRegister(): mac = " + mac);
    Callable<JSONObject> callable = () -> {
      OkHttpClient okHttpClient = new OkHttpClient();
      RequestBody requestBody = new FormBody.Builder()
              .add("phone", this.phone)
              .add("password", password)
              .add("mac", mac)
              .build();
      Request request = new Request.Builder()
              .url(ServerPath.REGISTER)
              .post(requestBody)
              .build();
      Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
        return new JSONObject(responseBody.string());
    };
    Future<JSONObject> future = pool.submit(callable);
    try {
      JSONObject jsonObject = future.get();
      boolean successful = jsonObject.getBoolean("result");
      if ( successful ) {
        new LoggedInUsers()
                .setAccount(this.phone)
                .setPassword(password)
                .setLastLoginTime(System.currentTimeMillis())
                .save();
        GlobalVariableTools.setUserId(context, jsonObject.getInt("userId"));
        callback.onDisplayRegister(true, R.string.msg_register_succeeded);
      } else {
        callback.onDisplayRegister(false, R.string.error_register_failed);
      }
    } catch ( InterruptedException | ExecutionException | JSONException e ) {
      Log.e(TAG, "register(): ", e);
      ToastUtils.show(context, R.string.error_server_disconnected);
    }
  }

  private void initiateModSDK() {
    // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，
    // 否则不起作用；如果没这个需求，可以不加这行代码
//    SMSSDK.setAskPermisionOnReadContact(true);
    // 创建EventHandler对象
    SMSSDK.registerEventHandler(this);
  }

  @Override
  public void afterEvent(int event, int result, Object data) {
    Log.w(TAG, "afterEvent(): event = " + event);
    Log.d(TAG, "afterEvent(): result = " + result);
    Log.w(TAG, "afterEvent(): data = " + data);
    if ( data instanceof Throwable && result == SMSSDK.RESULT_ERROR ) {
      /*
           错误编码
           返回值	错误描述
           200	验证成功
           405	AppKey为空
           406	AppKey无效
           456	国家代码或手机号码为空
           457	手机号码格式错误
           466	请求校验的验证码为空
           467	请求校验验证码频繁（5分钟内同一个appkey的同一个号码最多只能校验三次）
           468	验证码错误
           474	没有打开服务端验证开关
           */
      Throwable throwable = (Throwable) data;
      String msg = throwable.getMessage();
      try {
        callback.onDisplaySubmitVericode(false, new JSONObject(msg).getString("detail"));
      } catch ( JSONException e ) {
        e.printStackTrace();
      }
    } else switch ( result ) {
      case SMSSDK.RESULT_COMPLETE: {
        Log.i(TAG, "afterEvent(): 回调完成");
        //回调完成
        switch ( event ) {
          case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
            //提交验证码成功
            Log.d(TAG, "afterEvent(): 验证成功：event = " + event);
            callback.onDisplaySubmitVericode(true, R.string.msg_verify_successfully);
            break;
          case SMSSDK.EVENT_GET_VERIFICATION_CODE:
            //获取验证码成功
            Log.d(TAG, "afterEvent(): 获取验证码成功：event = " + event);
            callback.onDisplayGetVericode(true, R.string.msg_get_vericode_successfully);
            break;
          case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES:
            //返回支持发送验证码的国家列表
            Log.i(TAG, "afterEvent(): 返回支持发送验证码的国家列表");
            break;
        }
        break;
      }
      default:
        ( (Throwable) data ).printStackTrace();
        Log.e(TAG, "afterEvent(): 回调失败" + data);
        break;
    }
  }
}