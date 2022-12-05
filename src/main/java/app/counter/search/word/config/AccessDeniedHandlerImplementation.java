package app.counter.search.word.config;

import app.counter.search.word.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.counter.search.word.util.Util.convertJavaObjectToJson;

@Slf4j
@Component
public class AccessDeniedHandlerImplementation implements AccessDeniedHandler {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
    final ErrorResponse error = new ErrorResponse("ERR.01", "The credentials provided does not allow you to perform this request.");
    log.error("HTTP 403 Forbidden - {}", convertJavaObjectToJson(objectMapper, error));
    httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter().write(convertJavaObjectToJson(objectMapper, error));

  }
}
