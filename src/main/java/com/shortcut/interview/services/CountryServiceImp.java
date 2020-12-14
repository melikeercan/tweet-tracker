package com.shortcut.interview.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shortcut.interview.entities.nodes.Country;
import com.shortcut.interview.exceptions.DatabaseException;
import com.shortcut.interview.exceptions.EntityAlreadyExistsException;
import com.shortcut.interview.exceptions.EntityNotFoundException;
import com.shortcut.interview.exceptions.InvalidParameterException;
import com.shortcut.interview.repositories.CountryRepository;

import static com.shortcut.interview.utils.Constants.COUNTRY_LIST_IS_EMPTY;
import static com.shortcut.interview.utils.Constants.COUNTRY_NAME_IS_EMPTY_OR_NULL;

@Service
public class CountryServiceImp extends BaseServiceImp implements CountryService {

  private final CountryRepository countryRepository;

  public CountryServiceImp(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  @Override
  public HttpStatus saveAll(List<Country> countryList) {
    if (CollectionUtils.isEmpty(countryList)) {
      throw new InvalidParameterException(COUNTRY_LIST_IS_EMPTY);
    }
    countryList.forEach(country -> {
      Country found = findByName(country.getName());
      if (found != null) {
        throw new EntityAlreadyExistsException(Country.class, "saveAll", country.getName());
      }
      country.setId(UUID.randomUUID().toString());
      try {
        countryRepository.save(country);
      } catch (Exception ex) {
        throw new DatabaseException(Country.class, "saveAll", country.getName());
      }
    });
    return HttpStatus.CREATED;
  }

  @Override
  public List<Country> findAll() {
    try {
      return countryRepository.findAll();
    } catch (Exception ex) {
      throw new DatabaseException(Country.class, "findAll");
    }
  }

  @Override
  public Country findByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new InvalidParameterException(COUNTRY_NAME_IS_EMPTY_OR_NULL);
    }
    try {
      return countryRepository.findByName(name);
    } catch (Exception ex) {
      throw new DatabaseException(Country.class, "findByName", name);
    }
  }

  @Override
  public HttpStatus deleteAll() {
    try {
      countryRepository.deleteAll();
    } catch (Exception e) {
      throw new DatabaseException(Country.class, "deleteAll");
    }
    return HttpStatus.OK;
  }

  public List<Country> findMentionedCountries(String countryName) {
    if (countryName == null || countryName.isEmpty()) {
      throw new InvalidParameterException(COUNTRY_NAME_IS_EMPTY_OR_NULL);
    }
    Country country = findByName(countryName);
    if (country == null) {
      throw new EntityNotFoundException(Country.class, "name", countryName);
    }
    try {
      return countryRepository.findAllMentionedCountries(countryName);
    } catch (Exception ex) {
      throw new DatabaseException(Country.class, "findMentionedCountries", countryName);
    }
  }

  public List<Integer> findTweetsByTraffic(String fromCountryName, String toCountryName) {
    try {
      return countryRepository.findTweetsByTraffic(fromCountryName, toCountryName);
    } catch (Exception ex) {
      throw new DatabaseException(Country.class, "findTweetsByTraffic", fromCountryName,
          toCountryName);
    }
  }
}
