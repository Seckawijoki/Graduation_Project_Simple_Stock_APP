package com.seckawijoki.graduation_project.db.client;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/4 at 16:12.
 */

public class SimpleInformation extends DataSupport implements Parcelable{
  long informationId;
  String title;
  private transient Date dateTime;
  private transient String imageUri;

  public SimpleInformation() {
  }

  public long getInformationId() {
    return informationId;
  }

  public String getTitle() {
    return title;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public String getImageUri() {
    return imageUri;
  }

  public SimpleInformation setInformationId(long informationId) {
    this.informationId = informationId;
    return this;
  }

  public SimpleInformation setTitle(String title) {
    this.title = title;
    return this;
  }

  public SimpleInformation setDateTime(Date dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public SimpleInformation setImageUri(String imageUri) {
    this.imageUri = imageUri;
    return this;
  }

  protected SimpleInformation(Parcel in) {
    informationId = in.readLong();
    title = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(informationId);
    dest.writeString(title);
  }


  public static final Creator<SimpleInformation> CREATOR = new Creator<SimpleInformation>() {
    @Override
    public SimpleInformation createFromParcel(Parcel in) {
      return new SimpleInformation(in);
    }

    @Override
    public SimpleInformation[] newArray(int size) {
      return new SimpleInformation[size];
    }
  };

  @Override
  public String toString() {
    return "SimpleInformation{" +
            "informationId=" + informationId +
            ", title='" + title + '\'' +
            '}';
  }
}
