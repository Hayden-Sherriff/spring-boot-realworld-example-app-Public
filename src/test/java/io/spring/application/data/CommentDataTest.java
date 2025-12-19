package io.spring.application.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import io.spring.application.DateTimeCursor;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

public class CommentDataTest {

  @Test
  public void should_create_comment_data_with_all_fields() {
    DateTime now = new DateTime();
    ProfileData author = new ProfileData("author-id", "author", "bio", "image", false);

    CommentData commentData =
        new CommentData("comment-id", "Comment body", "article-id", now, now, author);

    assertThat(commentData.getId(), is("comment-id"));
    assertThat(commentData.getBody(), is("Comment body"));
    assertThat(commentData.getArticleId(), is("article-id"));
    assertThat(commentData.getCreatedAt(), is(now));
    assertThat(commentData.getUpdatedAt(), is(now));
    assertThat(commentData.getProfileData(), is(author));
  }

  @Test
  public void should_return_cursor_based_on_created_at() {
    DateTime createdAt = new DateTime(2023, 6, 15, 12, 0, 0);
    CommentData commentData =
        new CommentData("id", "body", "article", createdAt, new DateTime(), null);

    DateTimeCursor cursor = commentData.getCursor();

    assertThat(cursor, notNullValue());
    assertThat(cursor.getData(), is(createdAt));
  }

  @Test
  public void should_create_comment_data_with_no_args_constructor() {
    CommentData commentData = new CommentData();
    assertThat(commentData.getId(), is((String) null));
  }

  @Test
  public void should_set_and_get_body() {
    CommentData commentData = new CommentData();
    commentData.setBody("New comment body");
    assertThat(commentData.getBody(), is("New comment body"));
  }

  @Test
  public void should_set_and_get_article_id() {
    CommentData commentData = new CommentData();
    commentData.setArticleId("new-article-id");
    assertThat(commentData.getArticleId(), is("new-article-id"));
  }

  @Test
  public void should_be_equal_when_all_fields_match() {
    DateTime now = new DateTime();
    ProfileData author = new ProfileData("author-id", "author", "bio", "image", false);

    CommentData comment1 = new CommentData("id", "body", "article", now, now, author);
    CommentData comment2 = new CommentData("id", "body", "article", now, now, author);

    assertThat(comment1, is(comment2));
  }

  @Test
  public void should_have_same_hashcode_when_equal() {
    DateTime now = new DateTime();
    ProfileData author = new ProfileData("author-id", "author", "bio", "image", false);

    CommentData comment1 = new CommentData("id", "body", "article", now, now, author);
    CommentData comment2 = new CommentData("id", "body", "article", now, now, author);

    assertThat(comment1.hashCode(), is(comment2.hashCode()));
  }
}
