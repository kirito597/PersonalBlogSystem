// Video.java
package org.hc.spring.entity;

import java.time.LocalDateTime;

public class Video {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private String videoUrl;
    private String coverImage;
    private Integer duration;
    private Integer status;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String username;

    public Video() {}

    public Video(Integer userId, String title, String description, String videoUrl) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
    }

    // Getter和Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
    public LocalDateTime getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(LocalDateTime updatedTime) { this.updatedTime = updatedTime; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}