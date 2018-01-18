package com.seckawijoki.graduation_project.base;

import com.mob.MobApplication;
import com.mob.MobSDK;
import com.seckawijoki.graduation_project.functions.main.MainActivity;
import com.seckawijoki.graduation_project.util.OkHttpUtils;

import org.litepal.LitePal;

import cn.smssdk.SMSSDK;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/22 at 11:06.
 */

public class MyApplication extends MobApplication {
  private static String loginAccount;

  @Override
  public void onCreate() {
    super.onCreate();
    LitePal.initialize(this);
    OkHttpUtils.init();
    // 通过代码注册你的AppKey和AppSecret
//    MobSDK.init(this, "你的AppKey", "你的AppSecret");
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public MyApplication setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
    return this;
  }
}
