package com.shortcut.interview.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shortcut.interview.entities.dao.FetchInput;
import com.shortcut.interview.responses.RestCallResponse;
import com.shortcut.interview.services.FetchTwitterServiceImp;

import static com.shortcut.interview.utils.Constants.STARTED_FETCHING_TWEETS;
import static com.shortcut.interview.utils.Constants.STOPPED_FETCHING_TWEETS;

@RestController
@RequestMapping("/api/v0/fetch")
public class FetchTwitterController {
  private final FetchTwitterServiceImp fetchTwitterService;

  public FetchTwitterController(
      FetchTwitterServiceImp fetchTwitterService) {
    this.fetchTwitterService = fetchTwitterService;
  }

  @GetMapping("/")
  @ResponseBody
  RestCallResponse fetch(@RequestBody FetchInput input) {
    fetchTwitterService.fetch(input.getKeywords());
    return new RestCallResponse(HttpStatus.OK, STARTED_FETCHING_TWEETS);
  }

  @GetMapping("/stop")
  @ResponseBody
  RestCallResponse stopFetching() {
    fetchTwitterService.stopFetching();
    return new RestCallResponse(HttpStatus.OK, STOPPED_FETCHING_TWEETS);
  }
}
