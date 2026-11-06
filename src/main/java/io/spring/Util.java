package io.spring;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Util {
  private static final DateTimeFormatter ISO_UTC_MILLIS =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);

  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }

  public static String toIsoString(Instant instant) {
    return ISO_UTC_MILLIS.format(instant);
  }
}
