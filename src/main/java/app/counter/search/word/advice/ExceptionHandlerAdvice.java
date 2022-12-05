package app.counter.search.word.advice;

import app.counter.search.word.exception.CounterApiTechnicalException;
import app.counter.search.word.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

  @ExceptionHandler(CounterApiTechnicalException.class)
  public ResponseEntity<ErrorResponse> handleCounterApiTechnicalException(CounterApiTechnicalException exception) {

    ErrorResponse error = new ErrorResponse("001", exception.getMessage());

    log.error("Unexpected error occurred : {}", exception);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
