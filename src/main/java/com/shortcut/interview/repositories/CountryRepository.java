package com.shortcut.interview.repositories;

import java.util.List;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shortcut.interview.entities.nodes.Country;

@RepositoryRestResource(path = "countries")
public interface CountryRepository extends Repository<Country, String> {
  void save(Country country);

  List<Country> findAll();

  Country findByName(String name);

  void deleteAll();

  @Query(
      "MATCH (t:Tweet) - [r:SENT_FROM] - (c:Country {name:$countryName}) OPTIONAL MATCH (t) - [:MENTIONS_COUNTRY] - (c2:Country) return c2")
  List<Country> findAllMentionedCountries(String countryName);

  @Query(
      "MATCH (t:Tweet) - [r:SENT_FROM] - (c:Country {name:$fromCountryName}) MATCH (t) - [r2:MENTIONS_COUNTRY] - (c2:Country{name:$toCountryName}) return t.retweetCount")
  List<Integer> findTweetsByTraffic(String fromCountryName, String toCountryName);
}