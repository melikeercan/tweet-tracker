package com.shortcut.interview.services;

import com.shortcut.interview.entities.dao.Result;
import com.shortcut.interview.entities.dao.TweetTraffic;

public interface AnalysisService {
  Result findMentionedCountriesByCountryName(String countryName);

  Integer countReach(TweetTraffic tweetTraffic);
}
