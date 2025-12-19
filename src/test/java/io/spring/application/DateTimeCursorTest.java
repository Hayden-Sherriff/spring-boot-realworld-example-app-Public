package io.spring.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.Test;

public class DateTimeCursorTest {

  @Test
  public void should_create_cursor_with_datetime() {
    DateTime dateTime = new DateTime(2023, 1, 15, 10, 30, 0, DateTimeZone.UTC);
    DateTimeCursor cursor = new DateTimeCursor(dateTime);

    assertThat(cursor.getData(), is(dateTime));
  }

  @Test
  public void should_return_millis_as_string() {
    DateTime dateTime = new DateTime(2023, 1, 15, 10, 30, 0, DateTimeZone.UTC);
    DateTimeCursor cursor = new DateTimeCursor(dateTime);

    assertThat(cursor.toString(), is(String.valueOf(dateTime.getMillis())));
  }

  @Test
  public void should_parse_valid_cursor_string() {
    DateTime originalDateTime = new DateTime(2023, 1, 15, 10, 30, 0, DateTimeZone.UTC);
    String cursorString = String.valueOf(originalDateTime.getMillis());

    DateTime parsed = DateTimeCursor.parse(cursorString);

    assertThat(parsed, notNullValue());
    assertThat(parsed.getMillis(), is(originalDateTime.getMillis()));
  }

  @Test
  public void should_return_null_when_parsing_null_cursor() {
    DateTime parsed = DateTimeCursor.parse(null);

    assertThat(parsed, nullValue());
  }

  @Test
  public void should_parse_cursor_with_utc_timezone() {
    DateTime originalDateTime = new DateTime(2023, 6, 15, 12, 0, 0, DateTimeZone.UTC);
    String cursorString = String.valueOf(originalDateTime.getMillis());

    DateTime parsed = DateTimeCursor.parse(cursorString);

    assertThat(parsed.getZone(), is(DateTimeZone.UTC));
  }

  @Test
  public void should_handle_epoch_time() {
    DateTime epochDateTime = new DateTime(0, DateTimeZone.UTC);
    DateTimeCursor cursor = new DateTimeCursor(epochDateTime);

    assertThat(cursor.toString(), is("0"));
  }

  @Test
  public void should_parse_epoch_time() {
    DateTime parsed = DateTimeCursor.parse("0");

    assertThat(parsed.getMillis(), is(0L));
  }

  @Test
  public void should_roundtrip_datetime_through_cursor() {
    DateTime original = new DateTime(2023, 12, 25, 18, 45, 30, DateTimeZone.UTC);
    DateTimeCursor cursor = new DateTimeCursor(original);
    DateTime parsed = DateTimeCursor.parse(cursor.toString());

    assertThat(parsed.getMillis(), is(original.getMillis()));
  }
}
