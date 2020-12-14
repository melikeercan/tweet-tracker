package com.shortcut.interview.entities.dao;

import java.util.Date;
import java.util.Set;

import com.shortcut.interview.entities.nodes.Tweet;
import com.shortcut.interview.entities.relationships.TweetCountryRelationship;

public class TweetBuilder {

  private String id;

  private String text;

  private Long tweetId;

  private Integer retweetCount;

  private Date createdAt;

  private TweetCountryRelationship tweetCountryRelationship;

  private Set<TweetCountryRelationship> mentionedCountries;

  public TweetBuilder(String text) {
    this.text = text;
  }

  public TweetBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public TweetBuilder setText(String text) {
    this.text = text;
    return this;
  }

  public TweetBuilder setTweetId(Long tweetId) {
    this.tweetId = tweetId;
    return this;
  }

  public TweetBuilder setRetweetCount(Integer retweetCount) {
    this.retweetCount = retweetCount;
    return this;
  }

  public TweetBuilder setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public TweetBuilder setTweetCountryRelationship(
      TweetCountryRelationship tweetCountryRelationship) {
    this.tweetCountryRelationship = tweetCountryRelationship;
    return this;
  }

  public TweetBuilder setMentionedCountries(Set<TweetCountryRelationship> mentionedCountries) {
    this.mentionedCountries = mentionedCountries;
    return this;
  }

  public Tweet build() {
    return new Tweet(text, tweetId, retweetCount, createdAt, tweetCountryRelationship,
        mentionedCountries);
  }
}
