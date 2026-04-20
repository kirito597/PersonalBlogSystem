package org.hc.spring.service.impl;

import org.hc.spring.entity.Comment;
import org.hc.spring.mapper.CommentMapper;
import org.hc.spring.service.CommentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public boolean addComment(Comment comment) {
        return commentMapper.insertComment(comment) > 0;
    }

    @Override
    public List<Comment> getCommentsByTarget(Integer targetType, Integer targetId) {
        List<Comment> comments = commentMapper.selectByTarget(targetType, targetId);
        return buildCommentTree(comments);
    }

    @Override
    public boolean deleteComment(Integer commentId, Integer userId) {
        return commentMapper.deleteComment(commentId, userId) > 0;
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentMapper.selectById(id);
    }

    @Override
    public boolean addReply(Comment reply) {
        // 获取父评论来确定当前回复的层级
        Comment parentComment = commentMapper.selectById(reply.getParentId());
        if (parentComment != null) {
            reply.setLevel(parentComment.getLevel() + 1);
        } else {
            reply.setLevel(1);
        }
        return commentMapper.insertComment(reply) > 0;
    }

    @Override
    public List<Comment> getRepliesByParentId(Integer parentId) {
        return commentMapper.selectRepliesByParentId(parentId);
    }

    @Override
    public List<Comment> buildCommentTree(List<Comment> comments) {
        // 分离顶级评论和回复
        List<Comment> topLevelComments = comments.stream()
                .filter(comment -> comment.getParentId() == 0)
                .collect(Collectors.toList());

        List<Comment> allReplies = comments.stream()
                .filter(comment -> comment.getParentId() != 0)
                .collect(Collectors.toList());

        // 递归构建树形结构
        for (Comment topComment : topLevelComments) {
            buildReplies(topComment, allReplies);
        }

        return topLevelComments;
    }

    private void buildReplies(Comment parentComment, List<Comment> allReplies) {
        List<Comment> directReplies = allReplies.stream()
                .filter(reply -> reply.getParentId().equals(parentComment.getId()))
                .collect(Collectors.toList());

        parentComment.setReplies(directReplies);

        // 递归处理子回复
        for (Comment reply : directReplies) {
            buildReplies(reply, allReplies);
        }
    }
}