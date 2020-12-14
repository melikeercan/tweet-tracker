package com.shortcut.interview.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.shortcut.interview.exceptions.InvalidParameterException;

import static com.shortcut.interview.utils.Constants.SEARCH_KEYWORD_IS_EMPTY_OR_NULL;
import static com.shortcut.interview.utils.Constants.SEARCH_KEYWORD_LIST_IS_EMPTY;

@Service
public class FetchTwitterServiceImp extends BaseServiceImp implements FetchTwitterService {

  private final TwitterStreamServiceImp twitterStreamService;

  public FetchTwitterServiceImp(
      TwitterStreamServiceImp twitterStreamService) {
    this.twitterStreamService = twitterStreamService;
  }

  public HttpStatus fetch(List<String> keywords) {
    if (keywords == null || keywords.isEmpty()) {
      throw new InvalidParameterException(SEARCH_KEYWORD_LIST_IS_EMPTY);
    }
    for (String keyword : keywords) {
      if (keyword == null || keyword.isEmpty()) {
        throw new InvalidParameterException(SEARCH_KEYWORD_IS_EMPTY_OR_NULL);
      }
    }
    String[] array = new String[keywords.size()];
    keywords.toArray(array);
    twitterStreamService.startStreaming(array);
    return HttpStatus.OK;
  }

  public HttpStatus stopFetching() {
    return twitterStreamService.stopSteaming();
  }
}
