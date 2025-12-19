package io.spring.core.article;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArticleUpdateTest {

  private Article article;
  private DateTime originalCreatedAt;

  @BeforeEach
  public void setUp() {
    originalCreatedAt = new DateTime().minusDays(1);
    article =
        new Article(
            "Original Title",
            "Original Description",
            "Original Body",
            Arrays.asList("java", "spring"),
            "user123",
            originalCreatedAt);
  }

  @Test
  public void should_update_title_and_slug() {
    String newTitle = "New Title";
    article.update(newTitle, null, null);

    assertThat(article.getTitle(), is(newTitle));
    assertThat(article.getSlug(), is("new-title"));
    assertThat(article.getDescription(), is("Original Description"));
    assertThat(article.getBody(), is("Original Body"));
  }

  @Test
  public void should_update_description_only() {
    String newDescription = "New Description";
    article.update(null, newDescription, null);

    assertThat(article.getTitle(), is("Original Title"));
    assertThat(article.getDescription(), is(newDescription));
    assertThat(article.getBody(), is("Original Body"));
  }

  @Test
  public void should_update_body_only() {
    String newBody = "New Body";
    article.update(null, null, newBody);

    assertThat(article.getTitle(), is("Original Title"));
    assertThat(article.getDescription(), is("Original Description"));
    assertThat(article.getBody(), is(newBody));
  }

  @Test
  public void should_update_all_fields() {
    article.update("New Title", "New Description", "New Body");

    assertThat(article.getTitle(), is("New Title"));
    assertThat(article.getSlug(), is("new-title"));
    assertThat(article.getDescription(), is("New Description"));
    assertThat(article.getBody(), is("New Body"));
  }

  @Test
  public void should_not_update_when_values_are_empty() {
    article.update("", "", "");

    assertThat(article.getTitle(), is("Original Title"));
    assertThat(article.getSlug(), is("original-title"));
    assertThat(article.getDescription(), is("Original Description"));
    assertThat(article.getBody(), is("Original Body"));
  }

  @Test
  public void should_update_updatedAt_when_title_changes() {
    DateTime beforeUpdate = article.getUpdatedAt();
    article.update("New Title", null, null);

    assertThat(article.getUpdatedAt(), not(beforeUpdate));
  }

  @Test
  public void should_update_updatedAt_when_description_changes() {
    DateTime beforeUpdate = article.getUpdatedAt();
    article.update(null, "New Description", null);

    assertThat(article.getUpdatedAt(), not(beforeUpdate));
  }

  @Test
  public void should_update_updatedAt_when_body_changes() {
    DateTime beforeUpdate = article.getUpdatedAt();
    article.update(null, null, "New Body");

    assertThat(article.getUpdatedAt(), not(beforeUpdate));
  }

  @Test
  public void should_preserve_createdAt_when_updating() {
    article.update("New Title", "New Description", "New Body");

    assertThat(article.getCreatedAt(), is(originalCreatedAt));
  }

  @Test
  public void should_preserve_tags_when_updating() {
    article.update("New Title", null, null);

    assertThat(article.getTags().size(), is(2));
  }

  @Test
  public void should_preserve_userId_when_updating() {
    article.update("New Title", null, null);

    assertThat(article.getUserId(), is("user123"));
  }

  @Test
  public void should_create_article_with_unique_id() {
    Article article1 =
        new Article("Title 1", "Desc", "Body", Arrays.asList("java"), "user1");
    Article article2 =
        new Article("Title 2", "Desc", "Body", Arrays.asList("java"), "user2");

    assertThat(article1.getId(), notNullValue());
    assertThat(article2.getId(), notNullValue());
    assertThat(article1.getId(), not(article2.getId()));
  }

  @Test
  public void should_deduplicate_tags() {
    Article articleWithDuplicateTags =
        new Article(
            "Title", "Desc", "Body", Arrays.asList("java", "java", "spring", "spring"), "user1");

    assertThat(articleWithDuplicateTags.getTags().size(), is(2));
  }

  @Test
  public void should_be_equal_when_ids_are_same() {
    assertThat(article.equals(article), is(true));
  }

  @Test
  public void should_have_consistent_hashcode() {
    int hashCode1 = article.hashCode();
    int hashCode2 = article.hashCode();
    assertThat(hashCode1, is(hashCode2));
  }
}
