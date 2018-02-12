package com.seckawijoki.graduation_project.base;

import com.mob.MobApplication;
import com.mob.MobSDK;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

import org.litepal.LitePal;

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
    MobSDK.init(this);
//    MobPush.addTags(new String[2]);
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
