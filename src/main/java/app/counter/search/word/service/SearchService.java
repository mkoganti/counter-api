package app.counter.search.word.service;

import app.counter.search.word.model.WordFrequency;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@Slf4j
public class SearchService {

  public List<String> readFile() throws IOException {
    return Files.readAllLines(Paths.get("sample-paragraph.txt"));
  }

  private Long getWordFrequency(List<String> linesInFile, String wordToFind) {
    return linesInFile.stream().map(line -> stream(line.split("\\W+")).filter(word -> word.equalsIgnoreCase(wordToFind))
      .count()).collect(Collectors.summingLong(Long::longValue));
  }

  public WordFrequency countOccurrenceOfWords(List<String> words) throws IOException {

    List<String> linesInFile = readFile();
    log.info("Number of Lines in Paragraph : {}", linesInFile.size());
    log.info("Number of words for which frequency has to be recorded : {}", words.size());

    List<Map> wordsCounts = new ArrayList<>();
    words.forEach(word -> {
      Map<String, Long> wordCount = new HashMap<>();
      wordCount.put(word, getWordFrequency(linesInFile, word));
      wordsCounts.add(wordCount);
    });
    return new WordFrequency(wordsCounts);
  }

  public String getWordsWithMaximumOccurrences(Long numberOfResults) throws Exception {

    List<String> linesInFile = readFile();
    log.info("Number of Lines in Paragraph : {}", linesInFile.size());

    Set<String> words = linesInFile.stream().flatMap(line -> Arrays.stream(line.toLowerCase().split("\\W+")))
      .collect(Collectors.toSet());
    log.info("Number of unique words identified in paragraph : {}", words.size());

    Map<String, Long> wordCount = new HashMap<>();
    words.forEach(word -> wordCount.put(word, getWordFrequency(linesInFile, word)));
    log.info("Number of word and respective count pair in map : {}", wordCount.size());

    LinkedHashMap<String, Long> sortedWordCount = new LinkedHashMap<>();
    wordCount.entrySet()
      .stream()
      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
      .limit(numberOfResults)
      .forEachOrdered(sortedCount -> sortedWordCount.put(sortedCount.getKey(), sortedCount.getValue()));

    log.info("Number of records to be displayed after sorting : {}", sortedWordCount.size());

    return Joiner.on("\n").withKeyValueSeparator("|").join(sortedWordCount);
  }
}
