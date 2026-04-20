package org.hc.spring.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    private Integer id;
    private Integer userId;
    private Integer targetType; // 1:文章, 2:视频
    private Integer targetId;
    private String content;
    private Integer parentId;   // 父评论ID，0表示顶级评论
    private Integer status;
    private LocalDateTime createdTime;
    private String username;

    // 新增字段：回复列表和层级信息
    private List<Comment> replies; // 回复列表
    private Integer level;         // 评论层级：0-顶级评论，1-回复评论
    private String parentUsername; // 父评论用户名

    public Comment() {}

    public Comment(Integer userId, Integer targetType, Integer targetId, String content) {
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.content = content;
        this.parentId = 0;
        this.status = 1;
        this.level = 0;
    }

    // 回复评论的构造函数
    public Comment(Integer userId, Integer targetType, Integer targetId, String content, Integer parentId) {
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.content = content;
        this.parentId = parentId;
        this.status = 1;
        this.level = 1;
    }

    // Getter和Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getTargetType() { return targetType; }
    public void setTargetType(Integer targetType) { this.targetType = targetType; }
    public Integer getTargetId() { return targetId; }
    public void setTargetId(Integer targetId) { this.targetId = targetId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<Comment> getReplies() { return replies; }
    public void setReplies(List<Comment> replies) { this.replies = replies; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public String getParentUsername() { return parentUsername; }
    public void setParentUsername(String parentUsername) { this.parentUsername = parentUsername; }
}