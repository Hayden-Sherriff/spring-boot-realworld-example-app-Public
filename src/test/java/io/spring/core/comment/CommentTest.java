package io.spring.core.comment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class CommentTest {

  @Test
  public void should_create_comment_with_all_fields() {
    Comment comment = new Comment("This is a comment", "user123", "article456");

    assertThat(comment.getBody(), is("This is a comment"));
    assertThat(comment.getUserId(), is("user123"));
    assertThat(comment.getArticleId(), is("article456"));
    assertThat(comment.getId(), notNullValue());
    assertThat(comment.getCreatedAt(), notNullValue());
  }

  @Test
  public void should_generate_unique_ids_for_different_comments() {
    Comment comment1 = new Comment("Comment 1", "user1", "article1");
    Comment comment2 = new Comment("Comment 2", "user2", "article2");

    assertThat(comment1.getId(), not(comment2.getId()));
  }

  @Test
  public void should_be_equal_when_ids_are_same() {
    Comment comment = new Comment("Comment", "user1", "article1");
    assertThat(comment.equals(comment), is(true));
  }

  @Test
  public void should_not_be_equal_when_ids_are_different() {
    Comment comment1 = new Comment("Comment", "user1", "article1");
    Comment comment2 = new Comment("Comment", "user1", "article1");

    assertThat(comment1.equals(comment2), is(false));
  }

  @Test
  public void should_have_consistent_hashcode() {
    Comment comment = new Comment("Comment", "user1", "article1");
    int hashCode1 = comment.hashCode();
    int hashCode2 = comment.hashCode();

    assertThat(hashCode1, is(hashCode2));
  }

  @Test
  public void should_create_comment_with_empty_body() {
    Comment comment = new Comment("", "user123", "article456");

    assertThat(comment.getBody(), is(""));
    assertThat(comment.getId(), notNullValue());
  }

  @Test
  public void should_set_createdAt_to_current_time() {
    long beforeCreation = System.currentTimeMillis();
    Comment comment = new Comment("Comment", "user1", "article1");
    long afterCreation = System.currentTimeMillis();

    long createdAtMillis = comment.getCreatedAt().getMillis();
    assertThat(createdAtMillis >= beforeCreation, is(true));
    assertThat(createdAtMillis <= afterCreation, is(true));
  }
}
