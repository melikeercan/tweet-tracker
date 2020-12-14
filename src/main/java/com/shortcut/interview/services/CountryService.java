package com.shortcut.interview.services;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.shortcut.interview.entities.nodes.Country;

public interface CountryService {
  HttpStatus saveAll(List<Country> countryList);

  List<Country> findAll();

  Country findByName(String name);

  HttpStatus deleteAll();

  List<Country> findMentionedCountries(String countryName);

  List<Integer> findTweetsByTraffic(String fromCountryName, String toCountryName);
}
