package app.counter.search.word.config;

import app.counter.search.word.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.counter.search.word.util.Util.convertJavaObjectToJson;

@Slf4j
@Component
public class AuthenticationEntryPointImplementation implements AuthenticationEntryPoint {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
    final ErrorResponse error = new ErrorResponse("ERR.02", "Request cannot be actioned because the authentication credentials are not valid.");
    log.error("HTTP 401 Unauthorised - {}", convertJavaObjectToJson(objectMapper, error));
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter().write(convertJavaObjectToJson(objectMapper, error));

  }
}
