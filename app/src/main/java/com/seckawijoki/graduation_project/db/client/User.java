package com.seckawijoki.graduation_project.db.client;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/20 at 20:56.
 */

public class User extends DataSupport{
  private long userId;
  private String phone;
  private String nickname;
  private String email;
  private String portraitUri;
  private String portraitFileName;

  public String getPortraitFileName() {
    return portraitFileName;
  }

  public User setPortraitFileName(String portraitFileName) {
    this.portraitFileName = portraitFileName;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPortraitUri() {
    return portraitUri;
  }

  public User setPortraitUri(String portraitUri) {
    this.portraitUri = portraitUri;
    return this;
  }

  public long getUserId() {
    return userId;
  }

  public User setUserId(long userId) {
    this.userId = userId;
    return this;
  }
  public User setUserId(String userId) {
    this.userId = Long.parseLong(userId);
    return this;
  }

  public String getPhone() {
    return phone;
  }

  public User setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public String getNickname() {
    return nickname;
  }

  public User setNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }
}
