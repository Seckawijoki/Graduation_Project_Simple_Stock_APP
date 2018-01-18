package com.seckawijoki.graduation_project.db.client;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/23 at 19:37.
 */

public class LoggedInUsers extends DataSupport {
  public static final String ACCOUNT = "account";
  public static final String PASSWORD = "password";
  public static final String LAST_LOGIN_TIME = "lastLoginTime";
  private String account;
  private String password;
  private long lastLoginTime;
  public String getAccount() {
    return account;
  }
  public long getLastLoginTime() {
    return lastLoginTime;
  }
  public LoggedInUsers setLastLoginTime(long lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
    return this;
  }
  public String getPassword() {
    return password;
  }

  public LoggedInUsers setAccount(String account) {
    this.account = account;
    return this;
  }

  public LoggedInUsers setPassword(String password) {
    this.password = password;
    return this;
  }

  @Override
  public String toString() {
    return account + " | " + password + " | " + lastLoginTime;
  }
}
