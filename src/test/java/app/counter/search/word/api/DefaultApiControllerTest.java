package app.counter.search.word.api;


import app.counter.search.word.service.CounterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static app.counter.search.word.Helper.getFileAsText;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DefaultApiControllerTest {

  private static final String BASIC_AUTH = "Basic b3B0dXM6Y2FuZGlkYXRlcw==";
  private static final String BASIC_AUTH_INVALID = "Basic b3B0dM6Y2FuZGlkYXRlcw==";

  @Autowired
  CounterService counterService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private DefaultApiController defaultApiController;


  @Test
  public void testCountOccurrenceOfWords_whenReceivedListOfWordsToFind_thenShouldPerformSearchAndRetunResponse()
    throws Exception {

    // GIVEN
    String output = getFileAsText("test-data/output/word-count.json");
    String input = getFileAsText("test-data/input/words-to-be-searched.json");

    // WHEN - THEN
    mvc.perform(
      MockMvcRequestBuilders.post("/search")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", BASIC_AUTH)
        .content(input))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().json(output));

  }

  @Test
  public void testCountOccurrenceOfWords_whenAuthorizationHeaderIsMissing_thenShouldThrowUnAuthorizedException()
    throws Exception {

    // GIVEN
    String input = getFileAsText("test-data/input/words-to-be-searched.json");

    // WHEN - THEN
    mvc.perform(
      MockMvcRequestBuilders.post("/search")
        .contentType(MediaType.APPLICATION_JSON)
        .content(input))
      .andExpect(status().isUnauthorized());

  }

  @Test
  public void testCountOccurrenceOfWords_whenInvalidCredentialsAreProvided_thenShouldThrowUnAuthorizedException()
    throws Exception {

    // GIVEN
    String input = getFileAsText("test-data/input/words-to-be-searched.json");

    // WHEN - THEN
    mvc.perform(
      MockMvcRequestBuilders.post("/search")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", BASIC_AUTH_INVALID)
        .content(input))
      .andExpect(status().isUnauthorized());

  }

  @Test
  public void testGetWordsWithMaximumOccurrences_whenInvalidCredentialsAreProvided_thenShouldThrowUnAuthorizedException()
    throws Exception {

    // WHEN - THEN
    mvc.perform(
      MockMvcRequestBuilders.get("/top/20")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", BASIC_AUTH_INVALID))
      .andExpect(status().isUnauthorized());

  }

  @Test
  public void testGetWordsWithMaximumOccurrences_whenAuthorizationHeaderIsMissing_thenShouldThrowUnAuthorizedException()
    throws Exception {

    // WHEN - THEN
    mvc.perform(
      MockMvcRequestBuilders.get("/top/20")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isUnauthorized());

  }

  @Test
  public void testGetWordsWithMaximumOccurrences_whenRequestedForTop30Records_thenShouldFetchTop30()
    throws Exception {

    // GIVEN
    String output = getFileAsText("test-data/output/top-thirty-records.json");

    // WHEN - THEN
    mvc.perform(
      MockMvcRequestBuilders.get("/top/30")
        .header("Authorization", BASIC_AUTH))
      .andExpect(status().isOk());

  }

}