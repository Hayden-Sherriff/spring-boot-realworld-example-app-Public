package io.spring.core.favorite;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class ArticleFavoriteTest {

  @Test
  public void should_create_article_favorite() {
    ArticleFavorite favorite = new ArticleFavorite("article123", "user456");

    assertThat(favorite.getArticleId(), is("article123"));
    assertThat(favorite.getUserId(), is("user456"));
  }

  @Test
  public void should_be_equal_when_article_and_user_are_same() {
    ArticleFavorite favorite1 = new ArticleFavorite("article1", "user1");
    ArticleFavorite favorite2 = new ArticleFavorite("article1", "user1");

    assertThat(favorite1, is(favorite2));
  }

  @Test
  public void should_not_be_equal_when_articles_are_different() {
    ArticleFavorite favorite1 = new ArticleFavorite("article1", "user1");
    ArticleFavorite favorite2 = new ArticleFavorite("article2", "user1");

    assertThat(favorite1, not(favorite2));
  }

  @Test
  public void should_not_be_equal_when_users_are_different() {
    ArticleFavorite favorite1 = new ArticleFavorite("article1", "user1");
    ArticleFavorite favorite2 = new ArticleFavorite("article1", "user2");

    assertThat(favorite1, not(favorite2));
  }

  @Test
  public void should_have_same_hashcode_when_equal() {
    ArticleFavorite favorite1 = new ArticleFavorite("article1", "user1");
    ArticleFavorite favorite2 = new ArticleFavorite("article1", "user1");

    assertThat(favorite1.hashCode(), is(favorite2.hashCode()));
  }

  @Test
  public void should_have_different_hashcode_when_not_equal() {
    ArticleFavorite favorite1 = new ArticleFavorite("article1", "user1");
    ArticleFavorite favorite2 = new ArticleFavorite("article2", "user2");

    assertThat(favorite1.hashCode(), not(favorite2.hashCode()));
  }

  @Test
  public void should_be_equal_to_itself() {
    ArticleFavorite favorite = new ArticleFavorite("article1", "user1");

    assertThat(favorite.equals(favorite), is(true));
  }

  @Test
  public void should_not_be_equal_to_null() {
    ArticleFavorite favorite = new ArticleFavorite("article1", "user1");

    assertThat(favorite.equals(null), is(false));
  }
}
