package com.shortcut.interview.services;

import org.springframework.http.HttpStatus;

import twitter4j.TwitterStream;

public interface TwitterStreamService {
  TwitterStream establishConnection();

  void startStreaming(String[] hashtags);

  HttpStatus stopSteaming();
}
