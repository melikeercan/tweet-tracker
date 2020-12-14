package com.shortcut.interview.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shortcut.interview.entities.dao.Result;
import com.shortcut.interview.entities.dao.TweetTraffic;
import com.shortcut.interview.entities.nodes.Country;
import com.shortcut.interview.exceptions.EntityNotFoundException;
import com.shortcut.interview.exceptions.InvalidParameterException;

import static com.shortcut.interview.utils.Constants.TWEET_TRAFFIC_FROM_COUNTRY_IS_EMPTY_OR_NULL;
import static com.shortcut.interview.utils.Constants.TWEET_TRAFFIC_IS_NULL;
import static com.shortcut.interview.utils.Constants.TWEET_TRAFFIC_TO_COUNTRY_IS_EMPTY_OR_NULL;

@Service
public class AnalysisServiceImp extends BaseServiceImp implements AnalysisService {

  private final CountryServiceImp countryService;

  public AnalysisServiceImp(CountryServiceImp countryService) {
    this.countryService = countryService;
  }

  @Override
  public Result findMentionedCountriesByCountryName(String countryName) {
    List<Country> mentionedCountries = countryService.findMentionedCountries(countryName);
    Map<String, Long> counted = mentionedCountries.stream()
        .collect(Collectors.groupingBy(Country::getName, Collectors.counting()));
    return new Result(countryName, counted);
  }

  @Override
  public Integer countReach(TweetTraffic tweetTraffic) {
    if (tweetTraffic == null) {
      throw new InvalidParameterException(TWEET_TRAFFIC_IS_NULL);
    }
    String fromCountryName = tweetTraffic.getFromCountryName();
    String toCountryName = tweetTraffic.getToCountryName();
    if (fromCountryName == null || fromCountryName.isEmpty()) {
      throw new InvalidParameterException(TWEET_TRAFFIC_FROM_COUNTRY_IS_EMPTY_OR_NULL);
    }
    if (toCountryName == null || toCountryName.isEmpty()) {
      throw new InvalidParameterException(TWEET_TRAFFIC_TO_COUNTRY_IS_EMPTY_OR_NULL);
    }
    Country fromCountry = countryService.findByName(fromCountryName);
    if (fromCountry == null) {
      throw new EntityNotFoundException(Country.class, "name", fromCountryName);
    }
    Country toCountry = countryService.findByName(toCountryName);
    if (toCountry == null) {
      throw new EntityNotFoundException(Country.class, "name", toCountryName);
    }
    List<Integer> tweetList = countryService.findTweetsByTraffic(fromCountryName, toCountryName);
    return tweetList.stream().collect(Collectors.summingInt(Integer::intValue));
  }
}
