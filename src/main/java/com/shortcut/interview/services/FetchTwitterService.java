package com.shortcut.interview.services;

import java.util.List;

import org.springframework.http.HttpStatus;

public interface FetchTwitterService {
  HttpStatus fetch(List<String> keywords) throws InterruptedException;

  HttpStatus stopFetching();
}
