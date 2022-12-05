package app.counter.search.word.service;

import app.counter.search.word.model.WordFrequency;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static app.counter.search.word.Helper.getFileAsText;
import static org.assertj.core.api.Assertions.assertThat;


public class CounterServiceTest {

  CounterService counterService = new CounterService();

  @Test
  public void testGetWordFrequency_whenLinesOfParagraphAreGiven_thenShouldCountOccurrence() {

    //GIVEN
    String wordToFind = "find";
    List<String> linesInFile = new ArrayList<>();
    linesInFile.add("Now you find me however many times find occur in this sentence.");
    linesInFile.add("Can you believe I am finding find again and will find find any number of times");

    //WHEN
    Long frequency = counterService.getWordFrequency(linesInFile, wordToFind);

    //THEN
    assertThat(frequency.equals(5));

  }

  @Test
  public void testCountOccurrenceOfWords_whenListOfWordsToBeAccountedForComesIn_thenShouldGenerateCountOutput() throws IOException {

    //GIVEN
    List<String> wordsToBeSearchedFor = new ArrayList<>();
    wordsToBeSearchedFor.add("sed");
    wordsToBeSearchedFor.add("hello");

    //WHEN
    WordFrequency wordFrequency = counterService.countOccurrenceOfWords(wordsToBeSearchedFor);

    //THEN
    assertThat(wordFrequency.getCounts().size() == 2);
  }

  @Test
  public void testGetWordsWithMaximumOccurrences_whenRequestedForNNumberOfRecords_thenShouldDeliverTopN() throws Exception {

    //WHEN
    String expected = getFileAsText("test-data/output/top-thirty-records.json");
    String output = counterService.getWordsWithMaximumOccurrences(Long.valueOf(30));

    //THEN
    assertThat(output.equalsIgnoreCase(expected));

  }
}
