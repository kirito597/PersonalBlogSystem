package org.hc.spring.controller;

import org.hc.spring.entity.Article;
import org.hc.spring.entity.Comment;
import org.hc.spring.entity.User;
import org.hc.spring.entity.Video;
import org.hc.spring.service.ArticleService;
import org.hc.spring.service.CommentService;
import org.hc.spring.service.LikeService;
import org.hc.spring.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/interaction")
public class InteractionController {

    private final LikeService likeService;
    private final ArticleService articleService;
    private final VideoService videoService;
    private final CommentService commentService;

    public InteractionController(LikeService likeService, ArticleService articleService,
                                 VideoService videoService, CommentService commentService) {
        this.likeService = likeService;
        this.articleService = articleService;
        this.videoService = videoService;
        this.commentService = commentService;
    }

    // ==================== 点赞功能 ====================

    @PostMapping("/like/article/{articleId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likeArticle(@PathVariable Integer articleId,
                                                           @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        boolean success = likeService.likeArticle(user.getId(), articleId);
        result.put("success", success);
        result.put("message", success ? "点赞成功" : "点赞失败");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/unlike/article/{articleId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> unlikeArticle(@PathVariable Integer articleId,
                                                             @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        boolean success = likeService.unlikeArticle(user.getId(), articleId);
        result.put("success", success);
        result.put("message", success ? "取消点赞成功" : "取消点赞失败");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/like/video/{videoId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likeVideo(@PathVariable Integer videoId,
                                                         @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        boolean success = likeService.likeVideo(user.getId(), videoId);
        result.put("success", success);
        result.put("message", success ? "点赞成功" : "点赞失败");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/unlike/video/{videoId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> unlikeVideo(@PathVariable Integer videoId,
                                                           @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        boolean success = likeService.unlikeVideo(user.getId(), videoId);
        result.put("success", success);
        result.put("message", success ? "取消点赞成功" : "取消点赞失败");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check/article/{articleId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkArticleLike(@PathVariable Integer articleId,
                                                                @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        boolean isLiked = likeService.isArticleLikedByUser(user.getId(), articleId);
        result.put("isLiked", isLiked);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check/video/{videoId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkVideoLike(@PathVariable Integer videoId,
                                                              @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        boolean isLiked = likeService.isVideoLikedByUser(user.getId(), videoId);
        result.put("isLiked", isLiked);
        return ResponseEntity.ok(result);
    }

    // ==================== 热门内容 ====================

    @GetMapping("/popular/articles")
    public String popularArticles(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        List<Article> articles = articleService.getAllArticles().stream()
                .sorted((a1, a2) -> {
                    int score1 = (a1.getViewCount() != null ? a1.getViewCount() : 0) +
                            (a1.getLikeCount() != null ? a1.getLikeCount() : 0);
                    int score2 = (a2.getViewCount() != null ? a2.getViewCount() : 0) +
                            (a2.getLikeCount() != null ? a2.getLikeCount() : 0);
                    return Integer.compare(score2, score1);
                })
                .collect(Collectors.toList());
        model.addAttribute("articles", articles);
        return "interaction/popular-articles";
    }

    @GetMapping("/popular/videos")
    public String popularVideos(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        List<Video> videos = videoService.getAllVideos().stream()
                .sorted((v1, v2) -> {
                    int score1 = (v1.getViewCount() != null ? v1.getViewCount() : 0) +
                            (v1.getLikeCount() != null ? v1.getLikeCount() : 0);
                    int score2 = (v2.getViewCount() != null ? v2.getViewCount() : 0) +
                            (v2.getLikeCount() != null ? v2.getLikeCount() : 0);
                    return Integer.compare(score2, score1);
                })
                .collect(Collectors.toList());
        model.addAttribute("videos", videos);
        return "interaction/popular-videos";
    }

    // ==================== 评论功能 ====================

    @PostMapping("/comment/article/{articleId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> commentArticle(@PathVariable Integer articleId,
                                                              @RequestParam String content,
                                                              @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();

        if (content == null || content.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "评论内容不能为空");
            return ResponseEntity.badRequest().body(result);
        }

        // 关键：target_type = 1 表示文章评论
        Comment comment = new Comment(user.getId(), 1, articleId, content.trim());
        boolean success = commentService.addComment(comment);

        result.put("success", success);
        result.put("message", success ? "评论成功" : "评论失败");
        if (success && comment.getId() != null) {
            result.put("commentId", comment.getId());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/comment/video/{videoId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> commentVideo(@PathVariable Integer videoId,
                                                            @RequestParam String content,
                                                            @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();

        if (content == null || content.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "评论内容不能为空");
            return ResponseEntity.badRequest().body(result);
        }

        // 关键：target_type = 2 表示视频评论
        Comment comment = new Comment(user.getId(), 2, videoId, content.trim());
        boolean success = commentService.addComment(comment);

        result.put("success", success);
        result.put("message", success ? "评论成功" : "评论失败");
        if (success && comment.getId() != null) {
            result.put("commentId", comment.getId());
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/comments/article/{articleId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getArticleComments(@PathVariable Integer articleId) {
        Map<String, Object> result = new HashMap<>();
        // 关键：target_type = 1 表示只查询文章评论
        List<Comment> comments = commentService.getCommentsByTarget(1, articleId);
        result.put("success", true);
        result.put("comments", comments);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/comments/video/{videoId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getVideoComments(@PathVariable Integer videoId) {
        Map<String, Object> result = new HashMap<>();
        // 关键：target_type = 2 表示只查询视频评论
        List<Comment> comments = commentService.getCommentsByTarget(2, videoId);
        result.put("success", true);
        result.put("comments", comments);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/comment/delete/{commentId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Integer commentId,
                                                             @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        boolean success = commentService.deleteComment(commentId, user.getId());
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }

    // ==================== 回复功能 ====================

    @PostMapping("/reply/comment/{commentId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> replyComment(@PathVariable Integer commentId,
                                                            @RequestParam String content,
                                                            @SessionAttribute User user) {
        Map<String, Object> result = new HashMap<>();

        if (content == null || content.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "回复内容不能为空");
            return ResponseEntity.badRequest().body(result);
        }

        // 获取父评论信息
        Comment parentComment = commentService.getCommentById(commentId);
        if (parentComment == null) {
            result.put("success", false);
            result.put("message", "评论不存在");
            return ResponseEntity.badRequest().body(result);
        }

        // 创建回复评论
        Comment reply = new Comment(user.getId(), parentComment.getTargetType(),
                parentComment.getTargetId(), content.trim(), commentId);
        boolean success = commentService.addReply(reply);

        result.put("success", success);
        result.put("message", success ? "回复成功" : "回复失败");
        if (success && reply.getId() != null) {
            result.put("replyId", reply.getId());
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/replies/{commentId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCommentReplies(@PathVariable Integer commentId) {
        Map<String, Object> result = new HashMap<>();
        List<Comment> replies = commentService.getRepliesByParentId(commentId);
        result.put("success", true);
        result.put("replies", replies);
        return ResponseEntity.ok(result);
    }
}