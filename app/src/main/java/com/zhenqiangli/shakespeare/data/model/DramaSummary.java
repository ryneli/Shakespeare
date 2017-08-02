package com.zhenqiangli.shakespeare.data.model;

/**
 * Created by zhenqiangli on 8/2/17.
 */

public class DramaSummary {
  String title;
  String subtitle;
  int year;
  String genre;
  int lastAccess;

  public DramaSummary(String title, String subtitle, int year, String genre, int lastAccess) {
    this.title = title;
    this.subtitle = subtitle;
    this.year = year;
    this.genre = genre;
    this.lastAccess = lastAccess;
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
}
