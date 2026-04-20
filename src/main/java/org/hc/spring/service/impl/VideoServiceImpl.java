// VideoServiceImpl.java
package org.hc.spring.service.impl;

import org.hc.spring.entity.Video;
import org.hc.spring.mapper.VideoMapper;
import org.hc.spring.service.VideoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoMapper videoMapper;

    public VideoServiceImpl(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }

    @Override
    public boolean publishVideo(Video video) {
        return videoMapper.insertVideo(video) > 0;
    }

    @Override
    public Video getVideoById(Integer id) {
        return videoMapper.selectById(id);
    }

    @Override
    public List<Video> getAllVideos() {
        return videoMapper.selectAll();
    }

    @Override
    public List<Video> getUserVideos(Integer userId) {
        return videoMapper.selectByUserId(userId);
    }

    // 在 VideoServiceImpl.java 中修复 deleteVideo 方法
    @Override
    public boolean deleteVideo(Integer id, Integer userId) {
        Video video = videoMapper.selectById(id);
        // 只能删除自己的视频
        if (video != null && video.getUserId().equals(userId)) {
            return videoMapper.deleteVideo(id) > 0;
        }
        return false;
    }

    @Override
    public void increaseViewCount(Integer id) {
        videoMapper.updateViewCount(id);
    }

    // 在 VideoServiceImpl.java 中添加方法
    @Override
    public int getUserVideoCount(Integer userId) {
        return getUserVideos(userId).size();
    }

    @Override
    public int getUserTotalViews(Integer userId) {
        return getUserVideos(userId).stream()
                .mapToInt(video -> video.getViewCount() != null ? video.getViewCount() : 0)
                .sum();
    }

    @Override
    public int getUserTotalLikes(Integer userId) {
        return getUserVideos(userId).stream()
                .mapToInt(video -> video.getLikeCount() != null ? video.getLikeCount() : 0)
                .sum();
    }
}