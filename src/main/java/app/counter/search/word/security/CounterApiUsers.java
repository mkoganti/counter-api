package app.counter.search.word.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("counter-api-users")
@Getter
@Setter
public class CounterApiUsers {

  private List<CounterApiUser> users = new ArrayList<>();
}
