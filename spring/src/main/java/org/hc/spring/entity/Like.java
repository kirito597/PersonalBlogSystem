// Like.java
package org.hc.spring.entity;

import java.time.LocalDateTime;

public class Like {
    private Integer id;
    private Integer userId;
    private Integer targetType;
    private Integer targetId;
    private LocalDateTime createdTime;

    public Like() {}

    public Like(Integer userId, Integer targetType, Integer targetId) {
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
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
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
}