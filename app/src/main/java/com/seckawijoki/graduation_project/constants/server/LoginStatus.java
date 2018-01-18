package com.seckawijoki.graduation_project.constants.server;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/23 at 9:47.
 */


public interface LoginStatus{
  String KEY = "LOGIN_STATUS";
  int SUCCESSFUL = 1;
  int HAS_NOT_REGISTERED = 2;
//  int USER_NAME_ERROR = 3;
  int PHONE_ERROR = 4;
  int EMAIL_ERROR = 5;
  int PASSWORD_ERROR = 6;
  int DIFFERENT_MAC = 7;
  int HAS_LOGGED_IN_ON_ANOTHER_PHONE = 8;
  int HAS_LOGGED_IN_ON_THE_PHONE = 9;
  int FROZEN = 10;
  int LOGIN_TIME_ERROR = 11;
}
