package com.shortcut.interview.entities.nodes;

import java.util.UUID;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Country")
public class Country {
  @Id
  private String id;
  private String name;
  private String code;
  private Integer population;
  private Integer deathPerMillion;
  private Integer totalCasesPerMillion;

  private Country() {
  }

  public Country(String id, String name, String code, Integer population, Integer deathPerMillion) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.population = population;
    this.deathPerMillion = deathPerMillion;
  }

  public Country(String name, String code, Integer population, Integer deathPerMillion,
      Integer totalCasesPerMillion) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.code = code;
    this.population = population;
    this.deathPerMillion = deathPerMillion;
    this.totalCasesPerMillion = totalCasesPerMillion;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getPopulation() {
    return population;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }

  public Integer getDeathPerMillion() {
    return deathPerMillion;
  }

  public void setDeathPerMillion(Integer deathPerMillion) {
    this.deathPerMillion = deathPerMillion;
  }

  public Integer getTotalCasesPerMillion() {
    return totalCasesPerMillion;
  }

  public void setTotalCasesPerMillion(Integer totalCasesPerMillion) {
    this.totalCasesPerMillion = totalCasesPerMillion;
  }
}
