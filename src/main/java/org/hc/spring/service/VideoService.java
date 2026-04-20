// VideoService.java
package org.hc.spring.service;

import org.hc.spring.entity.Video;
import java.util.List;

// 在 VideoService.java 接口中添加方法
public interface VideoService {
    boolean publishVideo(Video video);
    Video getVideoById(Integer id);
    List<Video> getAllVideos();              // 所有用户的视频（公开）
    List<Video> getUserVideos(Integer userId);      // 指定用户的视频
    boolean deleteVideo(Integer id, Integer userId);
    void increaseViewCount(Integer id);

    // 新增统计方法
    int getUserVideoCount(Integer userId);
    int getUserTotalViews(Integer userId);
    int getUserTotalLikes(Integer userId);
}