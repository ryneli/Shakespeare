package com.zhenqiangli.shakespeare.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhenqiangli on 8/2/17.
 */

public class DramaSummary implements Parcelable {
  String title;
  String subtitle;
  int year;
  String genre;
  int lastAccess;
  int workId;

  public DramaSummary(String title, String subtitle, int year, String genre, int lastAccess, int workId) {
    this.title = title;
    this.subtitle = subtitle;
    this.year = year;
    this.genre = genre;
    this.lastAccess = lastAccess;
    this.workId = workId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public int getLastAccess() {
    return lastAccess;
  }

  public void setLastAccess(int lastAccess) {
    this.lastAccess = lastAccess;
  }

  public int getWorkId() {
    return workId;
  }

  public void setWorkId(int workId) {
    this.workId = workId;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(subtitle);
    dest.writeInt(year);
    dest.writeString(genre);
    dest.writeInt(lastAccess);
    dest.writeInt(workId);
  }
}
