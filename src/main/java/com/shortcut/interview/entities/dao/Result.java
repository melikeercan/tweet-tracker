package com.shortcut.interview.entities.dao;

import java.util.Map;

public class Result {
  private String countryName;
  private Map<String, Long> mentionedCountriesCount;

  private Result() {
  }

  public Result(String countryName,
      Map<String, Long> mentionedCountriesCount) {
    this.countryName = countryName;
    this.mentionedCountriesCount = mentionedCountriesCount;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public Map<String, Long> getMentionedCountriesCount() {
    return mentionedCountriesCount;
  }

  public void setMentionedCountriesCount(
      Map<String, Long> mentionedCountriesCount) {
    this.mentionedCountriesCount = mentionedCountriesCount;
  }
}
