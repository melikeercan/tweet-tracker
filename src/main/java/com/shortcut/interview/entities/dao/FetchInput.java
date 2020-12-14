package com.shortcut.interview.entities.dao;

import java.util.List;

public class FetchInput {
  private List<String> keywords;

  private FetchInput() {
  }

  public FetchInput(List<String> keywords) {
    this.keywords = keywords;
  }

  public List<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }
}
