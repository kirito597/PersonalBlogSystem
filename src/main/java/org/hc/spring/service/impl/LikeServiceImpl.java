// LikeServiceImpl.java
package org.hc.spring.service.impl;

import org.hc.spring.entity.Like;
import org.hc.spring.mapper.ArticleMapper;
import org.hc.spring.mapper.LikeMapper;
import org.hc.spring.mapper.VideoMapper;
import org.hc.spring.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;
    private final ArticleMapper articleMapper;
    private final VideoMapper videoMapper;

    public LikeServiceImpl(LikeMapper likeMapper, ArticleMapper articleMapper, VideoMapper videoMapper) {
        this.likeMapper = likeMapper;
        this.articleMapper = articleMapper;
        this.videoMapper = videoMapper;
    }

    @Override
    @Transactional
    public boolean likeArticle(Integer userId, Integer articleId) {
        // 检查是否已经点赞
        Like existingLike = likeMapper.selectByUserAndTarget(userId, 1, articleId);
        if (existingLike != null) {
            return false; // 已经点赞过了
        }

        // 添加点赞记录
        Like like = new Like(userId, 1, articleId);
        int result = likeMapper.insertLike(like);

        // 更新文章点赞数
        if (result > 0) {
            articleMapper.updateLikeCount(articleId, 1);
        }

        return result > 0;
    }

    @Override
    @Transactional
    public boolean unlikeArticle(Integer userId, Integer articleId) {
        // 删除点赞记录
        int result = likeMapper.deleteLike(userId, 1, articleId);

        // 更新文章点赞数
        if (result > 0) {
            articleMapper.updateLikeCount(articleId, -1);
        }

        return result > 0;
    }

    @Override
    @Transactional
    public boolean likeVideo(Integer userId, Integer videoId) {
        // 检查是否已经点赞
        Like existingLike = likeMapper.selectByUserAndTarget(userId, 2, videoId);
        if (existingLike != null) {
            return false; // 已经点赞过了
        }

        // 添加点赞记录
        Like like = new Like(userId, 2, videoId);
        int result = likeMapper.insertLike(like);

        // 更新视频点赞数
        if (result > 0) {
            videoMapper.updateLikeCount(videoId, 1);
        }

        return result > 0;
    }

    @Override
    @Transactional
    public boolean unlikeVideo(Integer userId, Integer videoId) {
        // 删除点赞记录
        int result = likeMapper.deleteLike(userId, 2, videoId);

        // 更新视频点赞数
        if (result > 0) {
            videoMapper.updateLikeCount(videoId, -1);
        }

        return result > 0;
    }

    @Override
    public boolean isArticleLikedByUser(Integer userId, Integer articleId) {
        Like like = likeMapper.selectByUserAndTarget(userId, 1, articleId);
        return like != null;
    }

    @Override
    public boolean isVideoLikedByUser(Integer userId, Integer videoId) {
        Like like = likeMapper.selectByUserAndTarget(userId, 2, videoId);
        return like != null;
    }
}