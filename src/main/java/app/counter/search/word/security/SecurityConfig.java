package app.counter.search.word.security;

import app.counter.search.word.config.AccessDeniedHandlerImplementation;
import app.counter.search.word.config.AuthenticationEntryPointImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private CounterApiUsers counterApiUsers;

  @Autowired
  private AuthenticationEntryPointImplementation authenticationEntryPointImplementation;
  @Autowired
  private AccessDeniedHandlerImplementation accessDeniedHandlerImplementation;

  /**
   * Performs in-memory authentication for users mentioned under 'counter-api-users' in application.yaml
   *
   * @param authenticationManagerBuilder
   */
  protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
    counterApiUsers
      .getUsers()
      .forEach(
        counterApiUser -> {
          try {
            authenticationManagerBuilder.inMemoryAuthentication()
              .withUser(counterApiUser.getUsername())
              .password(encoder.encode(counterApiUser.getPassword()))
              .roles("USER");
          } catch (Exception exception) {
            log.error("Exception while trying to authenticate");
          }
        });
  }

  /**
   * Performs request-authorising by pattern-matching URIs and Http Methods.
   * This has been configured to
   * - allow ROLE_USER authority users to access POST and GET
   *
   * @param httpSecurity
   * @throws Exception
   */
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.csrf().disable();

    httpSecurity
      .authorizeRequests()
      .antMatchers(HttpMethod.GET, "/top/**").hasAuthority("ROLE_USER")
      .antMatchers(HttpMethod.POST, "/search").hasAuthority("ROLE_USER")
      .anyRequest().denyAll()
      .and()
      .httpBasic()
      .authenticationEntryPoint(authenticationEntryPointImplementation)
      .and()
      .exceptionHandling()
      .accessDeniedHandler(accessDeniedHandlerImplementation);

  }
}
