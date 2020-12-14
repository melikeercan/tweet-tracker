package com.shortcut.interview.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shortcut.interview.entities.nodes.Country;

public class LocationUtils {

  public static String tokenMatcher(Country country, String location) {
    List<String> tokens = new ArrayList();
    tokens.add(country.getCode());
    tokens.add(country.getName());
    String patternString = "\\b(" + String.join("|", tokens) + ")\\b";
    Pattern pattern = Pattern.compile(patternString);
    Matcher matcher = pattern.matcher(location);
    if (matcher.find()) {
      return country.getName();
    }
    return null;
  }

  public static String findCountry(String location, List<Country> countryList) {
    if (location == null) {
      return null;
    }
    for (Country country : countryList) {
      String result = tokenMatcher(country, location);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  public static Set<String> findMentionedCountries(String text, List<Country> countryList) {
    if (text == null) {
      return null;
    }
    Set<String> countries = new HashSet<>();
    for (Country country : countryList) {
      String result = tokenMatcher(country, text);
      if (result != null) {
        countries.add(result);
      }
    }
    return countries;
  }
}
