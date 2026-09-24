package io.spring.application;

import java.time.OffsetDateTime;

public class DateTimeCursor extends PageCursor<OffsetDateTime> {

  public DateTimeCursor(OffsetDateTime data) {
    super(data);
  }

  @Override
  public String toString() {
    return String.valueOf(getData().toInstant().toEpochMilli());
  }

  public static OffsetDateTime parse(String cursor) {
    if (cursor == null) {
      return null;
    }
    return DateTimes.ofEpochMilli(Long.parseLong(cursor));
  }
}
