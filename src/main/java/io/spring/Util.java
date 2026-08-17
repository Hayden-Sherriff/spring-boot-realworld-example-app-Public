package io.spring;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Util {
  public static final DateTimeFormatter ISO_UTC_DATE_TIME =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ROOT)
          .withZone(ZoneOffset.UTC);

  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
}
