package io.spring.core.article;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class TagTest {

  @Test
  public void should_create_tag_with_name() {
    Tag tag = new Tag("java");
    assertThat(tag.getName(), is("java"));
    assertThat(tag.getId(), notNullValue());
  }

  @Test
  public void should_generate_unique_ids_for_different_tags() {
    Tag tag1 = new Tag("java");
    Tag tag2 = new Tag("spring");
    assertThat(tag1.getId(), not(tag2.getId()));
  }

  @Test
  public void should_be_equal_when_names_are_same() {
    Tag tag1 = new Tag("java");
    Tag tag2 = new Tag("java");
    assertThat(tag1, is(tag2));
  }

  @Test
  public void should_not_be_equal_when_names_are_different() {
    Tag tag1 = new Tag("java");
    Tag tag2 = new Tag("spring");
    assertThat(tag1, not(tag2));
  }

  @Test
  public void should_have_same_hashcode_when_names_are_same() {
    Tag tag1 = new Tag("java");
    Tag tag2 = new Tag("java");
    assertThat(tag1.hashCode(), is(tag2.hashCode()));
  }
}
