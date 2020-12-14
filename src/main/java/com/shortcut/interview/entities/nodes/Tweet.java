package com.shortcut.interview.entities.nodes;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.DateLong;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shortcut.interview.entities.relationships.TweetCountryRelationship;

@Node("Tweet")
public class Tweet {
  @Id
  private String id;

  private String text;

  private Long tweetId;

  private Integer retweetCount;

  @DateLong
  private Date createdAt;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Relationship(type = "SENT_FROM")
  private TweetCountryRelationship tweetCountryRelationship;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Relationship(type = "MENTIONS_COUNTRY")
  private Set<TweetCountryRelationship> mentionedCountries;

  public Tweet(String text, Long tweetId, Integer retweetCount, Date createdAt,
      TweetCountryRelationship tweetCountryRelationship,
      Set<TweetCountryRelationship> mentionedCountries) {
    this.id = UUID.randomUUID().toString();
    this.text = text;
    this.tweetId = tweetId;
    this.retweetCount = retweetCount;
    this.createdAt = createdAt;
    this.tweetCountryRelationship = tweetCountryRelationship;
    this.mentionedCountries = mentionedCountries;
  }

  public Tweet(String id, String text, Long tweetId, Integer retweetCount, Date createdAt,
      TweetCountryRelationship tweetCountryRelationship,
      Set<TweetCountryRelationship> mentionedCountries) {
    this.id = id;
    this.text = text;
    this.tweetId = tweetId;
    this.retweetCount = retweetCount;
    this.createdAt = createdAt;
    this.tweetCountryRelationship = tweetCountryRelationship;
    this.mentionedCountries = mentionedCountries;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getTweetId() {
    return tweetId;
  }

  public void setTweetId(Long tweetId) {
    this.tweetId = tweetId;
  }

  public Integer getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(Integer retweetCount) {
    this.retweetCount = retweetCount;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public TweetCountryRelationship getTweetCountryRelationship() {
    return tweetCountryRelationship;
  }

  public void setTweetCountryRelationship(TweetCountryRelationship tweetCountryRelationship) {
    this.tweetCountryRelationship = tweetCountryRelationship;
  }

  public Set<TweetCountryRelationship> getMentionedCountries() {
    return mentionedCountries;
  }

  public void setMentionedCountries(Set<TweetCountryRelationship> mentionedCountries) {
    this.mentionedCountries = mentionedCountries;
  }
}
