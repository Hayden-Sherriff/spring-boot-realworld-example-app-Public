package io.spring.application;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateTimes {
  private static final DateTimeFormatter ISO_UTC =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  private DateTimes() {}

  public static OffsetDateTime now() {
    return OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS);
  }

  public static OffsetDateTime ofEpochMilli(long millis) {
    return OffsetDateTime.ofInstant(java.time.Instant.ofEpochMilli(millis), ZoneOffset.UTC);
  }

  public static String format(OffsetDateTime value) {
    return ISO_UTC.format(value.withOffsetSameInstant(ZoneOffset.UTC));
  }
}
