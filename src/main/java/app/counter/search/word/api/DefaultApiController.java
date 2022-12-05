package app.counter.search.word.api;

import app.counter.search.word.exception.CounterApiTechnicalException;
import app.counter.search.word.model.WordSearch;
import app.counter.search.word.model.WordFrequency;
import app.counter.search.word.service.CounterService;
import app.counter.search.word.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class DefaultApiController {

  @Autowired
  private CounterService counterService;

  @PostMapping("search")
  public ResponseEntity<WordFrequency> countOccurrenceOfWords(@RequestHeader final Map<String, Object> requestHeaders,
                                                              @RequestBody WordSearch wordSearch)
    throws CounterApiTechnicalException {

    Util.generateTransactionId();
    log.info("Incoming HTTP Headers : {}", requestHeaders);

    try {

      return ResponseEntity.ok().body(counterService.countOccurrenceOfWords(wordSearch.getSearchKeyWords()));

    } catch (Exception exception) {

      throw new CounterApiTechnicalException(exception.getMessage());
    }
  }

  @GetMapping("top/{topWordsCount}")
  public ResponseEntity<String> getWordsWithMaximumOccurrences(@RequestHeader final Map<String, Object> requestHeaders,
                                                               @PathVariable(value = "topWordsCount") String topWordsCount)

    throws CounterApiTechnicalException {

    Util.generateTransactionId();
    log.info("Incoming HTTP Headers : {}", requestHeaders);

    try {

      return ResponseEntity.ok().body(counterService.getWordsWithMaximumOccurrences(Long.valueOf(topWordsCount)));

    } catch (Exception exception) {

      throw new CounterApiTechnicalException(exception.getMessage());
    }
  }
}
