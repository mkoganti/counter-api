package app.counter.search.word;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Objects;

public class Helper {

  public static String getFileAsText(final String path) throws IOException {
    return IOUtils.toString(Objects.requireNonNull(Helper.class.getClassLoader().getResourceAsStream(path)));
  }
}
