package app.counter.search.word.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CounterApiUser {

  private String username;
  private String password;
  private String role;
}
