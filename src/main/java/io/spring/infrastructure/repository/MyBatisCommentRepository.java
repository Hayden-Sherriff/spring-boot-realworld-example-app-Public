package io.spring.infrastructure.repository;

import io.spring.core.comment.Comment;
import io.spring.core.comment.CommentRepository;
import io.spring.core.comment.Status;
import io.spring.infrastructure.mybatis.mapper.CommentMapper;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBatisCommentRepository implements CommentRepository {
  private CommentMapper commentMapper;

  @Autowired
  public MyBatisCommentRepository(CommentMapper commentMapper) {
    this.commentMapper = commentMapper;
  }

  @Override
  public void save(Comment comment) {
    comment.setCreatedAt(DateTime.now());
    comment.setUpdatedAt(DateTime.now());
    comment.setStatus(Status.PUBLISHED.name());
    commentMapper.insert(comment);
  }

  @Override
  public Optional<Comment> findById(String articleId, String id) {
    return Optional.ofNullable(commentMapper.findById(articleId, id));
  }
  @Override
  public void updateStatus(Comment comment) {
    comment.setStatus(Status.REMOVED.name());

    commentMapper.updateStatus(
            comment.getId(),
            Status.REMOVED.name(),
            DateTime.now()
    );
  }

  @Override
  public void remove(Comment comment) {
    commentMapper.delete(comment.getId());
  }
}
