package io.spring.application.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import io.spring.application.DateTimeCursor;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

public class ArticleDataTest {

  @Test
  public void should_create_article_data_with_all_fields() {
    DateTime now = new DateTime();
    ProfileData author = new ProfileData("author-id", "author", "bio", "image", false);
    List<String> tags = Arrays.asList("java", "spring");

    ArticleData articleData =
        new ArticleData(
            "article-id",
            "test-slug",
            "Test Title",
            "Test Description",
            "Test Body",
            true,
            10,
            now,
            now,
            tags,
            author);

    assertThat(articleData.getId(), is("article-id"));
    assertThat(articleData.getSlug(), is("test-slug"));
    assertThat(articleData.getTitle(), is("Test Title"));
    assertThat(articleData.getDescription(), is("Test Description"));
    assertThat(articleData.getBody(), is("Test Body"));
    assertThat(articleData.isFavorited(), is(true));
    assertThat(articleData.getFavoritesCount(), is(10));
    assertThat(articleData.getCreatedAt(), is(now));
    assertThat(articleData.getUpdatedAt(), is(now));
    assertThat(articleData.getTagList(), is(tags));
    assertThat(articleData.getProfileData(), is(author));
  }

  @Test
  public void should_return_cursor_based_on_updated_at() {
    DateTime updatedAt = new DateTime(2023, 6, 15, 12, 0, 0);
    ArticleData articleData =
        new ArticleData(
            "id", "slug", "title", "desc", "body", false, 0, new DateTime(), updatedAt, null, null);

    DateTimeCursor cursor = articleData.getCursor();

    assertThat(cursor, notNullValue());
    assertThat(cursor.getData(), is(updatedAt));
  }

  @Test
  public void should_create_article_data_with_no_args_constructor() {
    ArticleData articleData = new ArticleData();
    assertThat(articleData.getFavoritesCount(), is(0));
    assertThat(articleData.isFavorited(), is(false));
  }

  @Test
  public void should_set_and_get_favorited() {
    ArticleData articleData = new ArticleData();
    articleData.setFavorited(true);
    assertThat(articleData.isFavorited(), is(true));
  }

  @Test
  public void should_set_and_get_favorites_count() {
    ArticleData articleData = new ArticleData();
    articleData.setFavoritesCount(25);
    assertThat(articleData.getFavoritesCount(), is(25));
  }
}
