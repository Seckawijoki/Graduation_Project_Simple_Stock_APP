package com.seckawijoki.graduation_project.db.client;

import android.os.Parcel;

import java.util.Date;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/27 at 22:19.
 */

public class Information extends SimpleInformation{
  private String content;
  private String author;
  private String classification;

  public Information() {
  }

  protected Information(Parcel in) {
    super(in);
  }

  @Override
  public Information setInformationId(long informationId) {
    super.setInformationId(informationId);
    return this;
  }

  @Override
  public Information setTitle(String title) {
    super.setTitle(title);
    return this;
  }

  @Override
  public Information setDateTime(Date dateTime) {
    super.setDateTime(dateTime);
    return this;
  }

  @Override
  public Information setImageUri(String imageUri) {
    super.setImageUri(imageUri);
    return this;
  }

  public String getContent() {
    return content;
  }

  public Information setContent(String content) {
    this.content = content;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public Information setAuthor(String author) {
    this.author = author;
    return this;
  }

  public String getClassification() {
    return classification;
  }

  public Information setClassification(String classification) {
    this.classification = classification;
    return this;
  }

  @Override
  public String toString() {
    return "Information{" +
            "content='" + content.substring(0, 10) + '\'' +
            ", informationId=" + informationId +
            ", title='" + title + '\'' +
            '}';
  }
}
