package org.hc.spring.service;

import org.hc.spring.entity.Comment;
import java.util.List;

public interface CommentService {
    boolean addComment(Comment comment);
    List<Comment> getCommentsByTarget(Integer targetType, Integer targetId);
    boolean deleteComment(Integer commentId, Integer userId);
    Comment getCommentById(Integer id);

    // 新增方法：添加回复
    boolean addReply(Comment reply);

    // 新增方法：获取评论的回复列表
    List<Comment> getRepliesByParentId(Integer parentId);

    // 新增方法：构建树形评论结构
    List<Comment> buildCommentTree(List<Comment> comments);
}