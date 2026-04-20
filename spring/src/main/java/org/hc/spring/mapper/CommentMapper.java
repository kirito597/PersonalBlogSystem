package org.hc.spring.mapper;

import org.hc.spring.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CommentMapper {
    int insertComment(Comment comment);
    List<Comment> selectByTarget(@Param("targetType") Integer targetType, @Param("targetId") Integer targetId);
    Comment selectById(Integer id);
    int deleteComment(@Param("id") Integer id, @Param("userId") Integer userId);

    // 新增方法：根据父评论ID查询回复
    List<Comment> selectRepliesByParentId(@Param("parentId") Integer parentId);

    // 新增方法：查询评论及其回复数量
    Integer getReplyCount(@Param("parentId") Integer parentId);
}