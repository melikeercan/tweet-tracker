package com.shortcut.interview.entities.dao;

public class TweetTraffic {
  private String fromCountryName;
  private String toCountryName;

  public TweetTraffic(String fromCountryName, String toCountryName) {
    this.fromCountryName = fromCountryName;
    this.toCountryName = toCountryName;
  }

  public String getFromCountryName() {
    return fromCountryName;
  }

  public void setFromCountryName(String fromCountryName) {
    this.fromCountryName = fromCountryName;
  }

  public String getToCountryName() {
    return toCountryName;
  }

  public void setToCountryName(String toCountryName) {
    this.toCountryName = toCountryName;
  }
}
