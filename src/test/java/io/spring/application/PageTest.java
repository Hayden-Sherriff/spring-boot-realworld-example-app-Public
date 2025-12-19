package io.spring.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class PageTest {

  @Test
  public void should_create_page_with_default_values() {
    Page page = new Page();
    assertThat(page.getOffset(), is(0));
    assertThat(page.getLimit(), is(20));
  }

  @Test
  public void should_create_page_with_custom_values() {
    Page page = new Page(10, 50);
    assertThat(page.getOffset(), is(10));
    assertThat(page.getLimit(), is(50));
  }

  @Test
  public void should_use_default_offset_when_negative() {
    Page page = new Page(-5, 50);
    assertThat(page.getOffset(), is(0));
    assertThat(page.getLimit(), is(50));
  }

  @Test
  public void should_use_default_offset_when_zero() {
    Page page = new Page(0, 50);
    assertThat(page.getOffset(), is(0));
  }

  @Test
  public void should_cap_limit_at_max_value() {
    Page page = new Page(0, 200);
    assertThat(page.getLimit(), is(100));
  }

  @Test
  public void should_use_default_limit_when_negative() {
    Page page = new Page(0, -10);
    assertThat(page.getLimit(), is(20));
  }

  @Test
  public void should_use_default_limit_when_zero() {
    Page page = new Page(0, 0);
    assertThat(page.getLimit(), is(20));
  }

  @Test
  public void should_accept_limit_at_max_boundary() {
    Page page = new Page(0, 100);
    assertThat(page.getLimit(), is(100));
  }

  @Test
  public void should_accept_limit_just_below_max() {
    Page page = new Page(0, 99);
    assertThat(page.getLimit(), is(99));
  }

  @Test
  public void should_accept_minimum_valid_limit() {
    Page page = new Page(0, 1);
    assertThat(page.getLimit(), is(1));
  }

  @Test
  public void should_accept_minimum_valid_offset() {
    Page page = new Page(1, 20);
    assertThat(page.getOffset(), is(1));
  }
}
