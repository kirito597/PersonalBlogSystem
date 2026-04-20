// LikeService.java
package org.hc.spring.service;

public interface LikeService {
    boolean likeArticle(Integer userId, Integer articleId);
    boolean unlikeArticle(Integer userId, Integer articleId);
    boolean likeVideo(Integer userId, Integer videoId);
    boolean unlikeVideo(Integer userId, Integer videoId);
    boolean isArticleLikedByUser(Integer userId, Integer articleId);
    boolean isVideoLikedByUser(Integer userId, Integer videoId);
}