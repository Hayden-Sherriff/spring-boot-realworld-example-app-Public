package io.spring.infrastructure.comment;

import io.spring.core.comment.Comment;
import io.spring.core.comment.CommentRepository;
import io.spring.core.comment.Status;
import io.spring.infrastructure.DbTestBase;
import io.spring.infrastructure.repository.MyBatisCommentRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({MyBatisCommentRepository.class})
public class MyBatisCommentRepositoryTest extends DbTestBase {
  @Autowired private CommentRepository commentRepository;

  @Test
  public void should_create_and_fetch_comment_success() {
    Comment comment = new Comment("content", "123", "456", Status.PUBLISHED.name());
    commentRepository.save(comment);

    Optional<Comment> optional = commentRepository.findById("456", comment.getId());
    Assertions.assertTrue(optional.isPresent());
    Assertions.assertEquals(optional.get(), comment);
  }

  @Test
  public void should_update_comment_status_success() {
    Comment comment = new Comment("content", "123", "456", Status.PUBLISHED.name());
    commentRepository.save(comment);

    // update status
    comment.setStatus(Status.REMOVED.name());
    commentRepository.updateStatus(comment);

    Optional<Comment> optional = commentRepository.findById("456", comment.getId());

    Assertions.assertTrue(optional.isPresent());
    Assertions.assertEquals(Status.REMOVED.name(), optional.get().getStatus());
  }

  @Test
  public void should_remove_comment_success() {
    Comment comment = new Comment("content", "123", "456", Status.PUBLISHED.name());
    commentRepository.save(comment);

    commentRepository.remove(comment);

    Optional<Comment> optional = commentRepository.findById("456", comment.getId());
    Assertions.assertFalse(optional.isPresent());
  }

  @Test
  public void should_return_empty_when_comment_not_found() {
    Optional<Comment> optional =
            commentRepository.findById("non-existing-article", "non-existing-id");

    Assertions.assertFalse(optional.isPresent());
  }


}
