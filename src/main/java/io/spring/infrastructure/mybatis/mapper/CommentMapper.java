package io.spring.infrastructure.mybatis.mapper;

import io.spring.core.comment.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;

@Mapper
public interface CommentMapper {
  void insert(@Param("comment") Comment comment);

  Comment findById(@Param("articleId") String articleId, @Param("id") String id);

  int updateStatus(
          @Param("id") String id,
          @Param("status") String status,
          @Param("updatedAt") DateTime updatedAt
  );

  void delete(@Param("id") String id);
}
