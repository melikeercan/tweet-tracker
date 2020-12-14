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
import com.shortcut.interview.entities.nodes.Country;
import com.shortcut.interview.services.CountryServiceImp;

import static com.shortcut.interview.utils.Constants.CREATED_COUNTRIES_SUCCESSFULLY;
import static com.shortcut.interview.utils.Constants.DELETED_COUNTRIES_SUCCESSFULLY;
import static com.shortcut.interview.utils.Constants.LISTED_COUNTRIES_SUCCESSFULLY;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)
public class CountryControllerTests {
  ObjectMapper mapper = new ObjectMapper();
  String url = "/api/v0/countries/";
  String name = "Test_Country_Name";
  Country country = new Country(name, "Test_Country_Code", 5000000, 100, 40);
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CountryServiceImp countryService;

  @Test
  public void shouldGetAllCountries() throws Exception {
    List<Country> list = singletonList(country);
    String contentExpected = mapper.writeValueAsString(list);
    System.out.println(contentExpected);
    when(countryService.findAll()).thenReturn(list);
    mockMvc.perform(get(url))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(LISTED_COUNTRIES_SUCCESSFULLY)))
        .andExpect(content().string(containsString(contentExpected)));
  }

  @Test
  public void shouldAddACountries() throws Exception {
    List<Country> countryList = singletonList(country);
    when(countryService.saveAll(countryList)).thenReturn(HttpStatus.CREATED);
    String input = mapper.writeValueAsString(countryList);
    String contentExpected = mapper.writeValueAsString(countryList);
    mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(input))
        .andDo(print())
        .andExpect(content().string(containsString(contentExpected)))
        .andExpect(content().string(containsString(CREATED_COUNTRIES_SUCCESSFULLY)));
  }

  @Test
  public void shouldDeleteAllCountries() throws Exception {
    when(countryService.deleteAll()).thenReturn(HttpStatus.OK);
    mockMvc.perform(delete(url))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(DELETED_COUNTRIES_SUCCESSFULLY)));
  }
}
