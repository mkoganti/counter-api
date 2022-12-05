package app.counter.search.word.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class Util {

  public static void generateTransactionId() {
    final String transactionId = UUID.randomUUID().toString();
    MDC.put("transaction-id", transactionId);
    log.info("Generated transaction-id for this process : {}", transactionId);
  }

  public static String convertJavaObjectToJson(ObjectMapper objectMapper, Object object) {
    String jsonString = "";
    try {
      jsonString = objectMapper.writeValueAsString(object);

    } catch (JsonProcessingException jsonProcessingException) {
      log.error("Exception while converting object : {} to JSON for logging outbound response", object.getClass(), jsonProcessingException);
    }
    return jsonString;
  }
}
