package com.shortcut.interview.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortcut.interview.entities.dao.FetchInput;
import com.shortcut.interview.services.FetchTwitterServiceImp;

import static com.shortcut.interview.utils.Constants.STARTED_FETCHING_TWEETS;
import static com.shortcut.interview.utils.Constants.STOPPED_FETCHING_TWEETS;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FetchTwitterController.class)
public class FetchTwitterControllerTests {
  ObjectMapper mapper = new ObjectMapper();
  String url = "/api/v0/fetch/";
  String keyword = "Test_Keyword";
  List<String> keywords = singletonList(keyword);
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private FetchTwitterServiceImp fetchTwitterService;

  @Test
  public void shouldStartFetching() throws Exception {
    when(fetchTwitterService.fetch(keywords)).thenReturn(HttpStatus.OK);
    FetchInput fetchInput = new FetchInput(keywords);
    String input = mapper.writeValueAsString(fetchInput);
    mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).content(input))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(STARTED_FETCHING_TWEETS)));
  }

  @Test
  public void shouldStopFetching() throws Exception {
    String url = "/api/v0/fetch/stop";
    when(fetchTwitterService.fetch(keywords)).thenReturn(HttpStatus.OK);
    mockMvc.perform(get(url))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(STOPPED_FETCHING_TWEETS)));
  }

}
