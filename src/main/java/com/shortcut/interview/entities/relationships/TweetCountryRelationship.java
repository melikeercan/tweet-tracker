package com.shortcut.interview.entities.relationships;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shortcut.interview.entities.nodes.Country;
import com.shortcut.interview.entities.nodes.Tweet;

@RelationshipProperties
public class TweetCountryRelationship {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @TargetNode
  private Country country;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Tweet tweet;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String location;

  private TweetCountryRelationship() {
  }

  public TweetCountryRelationship(Country country, Tweet tweet) {
    this.country = country;
    this.tweet = tweet;
  }

  public TweetCountryRelationship(Country country, Tweet tweet, String location) {
    this.country = country;
    this.tweet = tweet;
    this.location = location;
  }

  public Country getCountry() {
    return country;
  }

  public TweetCountryRelationship setCountry(Country country) {
    this.country = country;
    return this;
  }

  public Tweet getTweet() {
    return tweet;
  }

  public TweetCountryRelationship setTweet(Tweet tweet) {
    this.tweet = tweet;
    return this;
  }

  public String getLocation() {
    return location;
  }

  public TweetCountryRelationship setLocation(String location) {
    this.location = location;
    return this;
  }
}
