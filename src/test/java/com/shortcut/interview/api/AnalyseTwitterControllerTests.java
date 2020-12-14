package com.shortcut.interview.api;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortcut.interview.entities.dao.Result;
import com.shortcut.interview.entities.dao.TweetTraffic;
import com.shortcut.interview.exceptions.InvalidParameterException;
import com.shortcut.interview.services.AnalysisServiceImp;

import static com.shortcut.interview.utils.Constants.LISTED_COUNTRIES_SUCCESSFULLY;
import static com.shortcut.interview.utils.Constants.RETURNED_REACH_COUNT_SUCCESSFULLY;
import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AnalyseTwitterController.class)
public class AnalyseTwitterControllerTests {
  ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AnalysisServiceImp analysisService;

  @Test
  public void shouldGetMentionedCountries() throws Exception {
    String name = "Test_Country_Name";
    Map<String, Long> mentionedCountriesCount = singletonMap(name, 1L);
    Result result = new Result(name, mentionedCountriesCount);
    when(analysisService.findMentionedCountriesByCountryName(name)).thenReturn(result);
    String url = String.format("/api/v0/analyse/country=%s", name);
    mockMvc.perform(get(url))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(LISTED_COUNTRIES_SUCCESSFULLY)))
        .andExpect(content().string(containsString(name)));
  }

  @Test
  public void shouldThrowInvalidParameterExWhenNameIsNull() throws Exception {
    String name = "";
    when(analysisService.findMentionedCountriesByCountryName(name)).thenThrow(
        InvalidParameterException.class);
    String url = String.format("/api/v0/analyse/country=%s", name);
    mockMvc.perform(get(url))
        .andDo(print())
        .andExpect(status().isNotAcceptable())
        .andExpect(content().string(containsString("NOT_ACCEPTABLE")));
  }

  @Test
  public void shouldCountReach() throws Exception {
    String from = "Test_Country_Name_From";
    String to = "Test_Country_Name_To";
    TweetTraffic tweetTraffic = new TweetTraffic(from, to);
    when(analysisService.countReach(tweetTraffic)).thenReturn(0);
    String url = "/api/v0/analyse/";
    String input = mapper.writeValueAsString(tweetTraffic);
    mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(input))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(RETURNED_REACH_COUNT_SUCCESSFULLY)));
  }
}
