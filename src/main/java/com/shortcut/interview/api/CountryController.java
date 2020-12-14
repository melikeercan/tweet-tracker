package com.shortcut.interview.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shortcut.interview.entities.nodes.Country;
import com.shortcut.interview.exceptions.DatabaseException;
import com.shortcut.interview.exceptions.EntityAlreadyExistsException;
import com.shortcut.interview.exceptions.InvalidParameterException;
import com.shortcut.interview.responses.RestCallResponse;
import com.shortcut.interview.services.CountryServiceImp;

import static com.shortcut.interview.utils.Constants.CREATED_COUNTRIES_SUCCESSFULLY;
import static com.shortcut.interview.utils.Constants.DELETED_COUNTRIES_SUCCESSFULLY;
import static com.shortcut.interview.utils.Constants.LISTED_COUNTRIES_SUCCESSFULLY;

@RestController
@RequestMapping("/api/v0/countries")
public class CountryController {

  private final CountryServiceImp countryService;

  public CountryController(CountryServiceImp countryService) {
    this.countryService = countryService;
  }

  @GetMapping("/")
  @ResponseBody
  RestCallResponse findAll() {
    return new RestCallResponse(HttpStatus.OK, LISTED_COUNTRIES_SUCCESSFULLY,
        countryService.findAll());
  }

  @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  RestCallResponse create(@RequestBody List<Country> countryList)
      throws InvalidParameterException, EntityAlreadyExistsException, DatabaseException {
    countryService.saveAll(countryList);
    return new RestCallResponse(HttpStatus.CREATED, CREATED_COUNTRIES_SUCCESSFULLY, countryList);
  }

  @DeleteMapping("/")
  @ResponseBody
  RestCallResponse deleteAll() {
    return new RestCallResponse(HttpStatus.OK, DELETED_COUNTRIES_SUCCESSFULLY,
        countryService.deleteAll());
  }

}
