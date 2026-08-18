package io.spring;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Util {
  private static final DateTimeFormatter ISO_UTC_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").withZone(ZoneOffset.UTC);

  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }

  public static String formatIsoUtc(Instant instant) {
    return ISO_UTC_FORMATTER.format(instant);
  }
}
