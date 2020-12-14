package com.shortcut.interview.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shortcut.interview.entities.dao.TweetTraffic;
import com.shortcut.interview.responses.RestCallResponse;
import com.shortcut.interview.services.AnalysisServiceImp;

import static com.shortcut.interview.utils.Constants.LISTED_COUNTRIES_SUCCESSFULLY;
import static com.shortcut.interview.utils.Constants.RETURNED_REACH_COUNT_SUCCESSFULLY;

@RestController
@RequestMapping("/api/v0/analyse")
public class AnalyseTwitterController {

  private final AnalysisServiceImp analysisService;

  public AnalyseTwitterController(
      AnalysisServiceImp analysisService) {
    this.analysisService = analysisService;
  }

  @GetMapping("/country={countryName}")
  RestCallResponse getMentionedCountries(@PathVariable("countryName") String countryName) {
    return new RestCallResponse(HttpStatus.OK, LISTED_COUNTRIES_SUCCESSFULLY,
        analysisService.findMentionedCountriesByCountryName(countryName));
  }

  @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  RestCallResponse countReach(@RequestBody TweetTraffic tweetTraffic) {
    return new RestCallResponse(HttpStatus.OK, RETURNED_REACH_COUNT_SUCCESSFULLY,
        analysisService.countReach(tweetTraffic));
  }

}
