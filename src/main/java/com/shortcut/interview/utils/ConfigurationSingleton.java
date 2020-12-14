package com.shortcut.interview.utils;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static com.shortcut.interview.utils.Constants.ACCESS_KEY;
import static com.shortcut.interview.utils.Constants.ACCESS_SECRET;
import static com.shortcut.interview.utils.Constants.CONSUMER_KEY;
import static com.shortcut.interview.utils.Constants.CONSUMER_SECRET;

public class ConfigurationSingleton {

  private static ConfigurationSingleton single_instance = null;
  public Configuration configuration;

  private ConfigurationSingleton() {
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(CONSUMER_KEY)
        .setOAuthConsumerSecret(CONSUMER_SECRET)
        .setOAuthAccessToken(ACCESS_KEY)
        .setOAuthAccessTokenSecret(ACCESS_SECRET);
    this.setConfiguration(configurationBuilder.build());
  }

  public static ConfigurationSingleton getInstance() {
    if (single_instance == null) {
      single_instance = new ConfigurationSingleton();
    }
    return single_instance;
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }
}
