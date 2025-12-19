package io.spring.core.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import io.spring.core.article.Article;
import io.spring.core.comment.Comment;
import io.spring.core.user.User;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorizationServiceTest {

  private User articleAuthor;
  private User commentAuthor;
  private User otherUser;
  private Article article;
  private Comment commentByArticleAuthor;
  private Comment commentByOtherUser;

  @BeforeEach
  public void setUp() {
    articleAuthor = new User("author@example.com", "author", "pass", "bio", "image");
    commentAuthor = new User("commenter@example.com", "commenter", "pass", "bio", "image");
    otherUser = new User("other@example.com", "other", "pass", "bio", "image");

    article =
        new Article(
            "Test Article", "Description", "Body", Arrays.asList("java"), articleAuthor.getId());

    commentByArticleAuthor =
        new Comment("Comment by author", articleAuthor.getId(), article.getId());
    commentByOtherUser = new Comment("Comment by other", commentAuthor.getId(), article.getId());
  }

  @Test
  public void should_allow_article_author_to_write_article() {
    boolean canWrite = AuthorizationService.canWriteArticle(articleAuthor, article);

    assertThat(canWrite, is(true));
  }

  @Test
  public void should_not_allow_other_user_to_write_article() {
    boolean canWrite = AuthorizationService.canWriteArticle(otherUser, article);

    assertThat(canWrite, is(false));
  }

  @Test
  public void should_allow_article_author_to_write_any_comment_on_their_article() {
    boolean canWrite =
        AuthorizationService.canWriteComment(articleAuthor, article, commentByOtherUser);

    assertThat(canWrite, is(true));
  }

  @Test
  public void should_allow_comment_author_to_write_their_own_comment() {
    boolean canWrite =
        AuthorizationService.canWriteComment(commentAuthor, article, commentByOtherUser);

    assertThat(canWrite, is(true));
  }

  @Test
  public void should_not_allow_other_user_to_write_comment_they_did_not_create() {
    boolean canWrite =
        AuthorizationService.canWriteComment(otherUser, article, commentByOtherUser);

    assertThat(canWrite, is(false));
  }

  @Test
  public void should_allow_article_author_to_write_their_own_comment() {
    boolean canWrite =
        AuthorizationService.canWriteComment(articleAuthor, article, commentByArticleAuthor);

    assertThat(canWrite, is(true));
  }

  @Test
  public void should_not_allow_comment_author_to_write_other_comments() {
    boolean canWrite =
        AuthorizationService.canWriteComment(commentAuthor, article, commentByArticleAuthor);

    assertThat(canWrite, is(false));
  }
}
