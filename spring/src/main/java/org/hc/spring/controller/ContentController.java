// ContentController.java - 修复完整版本
package org.hc.spring.controller;

import org.hc.spring.entity.Article;
import org.hc.spring.entity.User;
import org.hc.spring.entity.Video;
import org.hc.spring.service.ArticleService;
import org.hc.spring.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/content")
public class ContentController {

    private final ArticleService articleService;
    private final VideoService videoService;

    public ContentController(ArticleService articleService, VideoService videoService) {
        this.articleService = articleService;
        this.videoService = videoService;
    }

    // 文章列表
    @GetMapping("/articles")
    public String articles(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("articles", articleService.getAllArticles());
        return "content/articles";
    }

    // 发布文章页面
    @GetMapping("/articles/publish")
    public String publishArticlePage(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "content/publish-article";
    }

    // 处理文章发布
    @PostMapping("/articles/publish")
    public String publishArticle(@RequestParam String title,
                                 @RequestParam String content,
                                 @RequestParam(required = false) MultipartFile coverImage,
                                 @SessionAttribute User user,
                                 Model model) {
        model.addAttribute("user", user);

        Article article = new Article(user.getId(), title, content);

        // 处理封面图片上传
        if (coverImage != null && !coverImage.isEmpty()) {
            String fileName = saveFile(coverImage, "images");
            article.setCoverImage(fileName);
        }

        boolean success = articleService.publishArticle(article);
        if (success) {
            return "redirect:/content/articles";
        } else {
            model.addAttribute("error", "发布失败");
            return "content/publish-article";
        }
    }

    // 文章详情
    @GetMapping("/articles/{id}")
    public String articleDetail(@PathVariable Integer id,
                                @SessionAttribute User user,
                                Model model) {
        model.addAttribute("user", user);
        Article article = articleService.getArticleById(id);
        if (article != null) {
            articleService.increaseViewCount(id);
            model.addAttribute("article", article);
            return "content/article-detail";
        }
        return "redirect:/content/articles";
    }

    // 视频列表
    @GetMapping("/videos")
    public String videos(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("videos", videoService.getAllVideos());
        return "content/videos";
    }

    // 发布视频页面
    @GetMapping("/videos/publish")
    public String publishVideoPage(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "content/publish-video";
    }

    // 处理视频发布
    @PostMapping("/videos/publish")
    public String publishVideo(@RequestParam String title,
                               @RequestParam String description,
                               @RequestParam String videoUrl,
                               @RequestParam(required = false) MultipartFile coverImage,
                               @SessionAttribute User user,
                               Model model) {
        model.addAttribute("user", user);

        Video video = new Video(user.getId(), title, description, videoUrl);

        // 处理封面图片上传
        if (coverImage != null && !coverImage.isEmpty()) {
            String fileName = saveFile(coverImage, "images");
            video.setCoverImage(fileName);
        }

        boolean success = videoService.publishVideo(video);
        if (success) {
            return "redirect:/content/videos";
        } else {
            model.addAttribute("error", "发布失败");
            return "content/publish-video";
        }
    }

    // 视频详情
    @GetMapping("/videos/{id}")
    public String videoDetail(@PathVariable Integer id,
                              @SessionAttribute User user,
                              Model model) {
        model.addAttribute("user", user);
        Video video = videoService.getVideoById(id);
        if (video != null) {
            videoService.increaseViewCount(id);
            model.addAttribute("video", video);
            return "content/video-detail";
        }
        return "redirect:/content/videos";
    }

    // 在 ContentController.java 中确保以下方法正确
    @GetMapping("/my-articles")
    public String myArticles(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        // 只获取当前用户的文章
        model.addAttribute("articles", articleService.getUserArticles(user.getId()));
        return "content/my-articles";
    }

    @GetMapping("/my-videos")
    public String myVideos(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        // 只获取当前用户的视频
        model.addAttribute("videos", videoService.getUserVideos(user.getId()));
        return "content/my-videos";
    }

    // 在 ContentController.java 中添加删除方法
    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Integer id,
                                @SessionAttribute User user) {
        // 只能删除自己的文章
        articleService.deleteArticle(id, user.getId());
        return "redirect:/content/my-articles";
    }

    @GetMapping("/videos/delete/{id}")
    public String deleteVideo(@PathVariable Integer id,
                              @SessionAttribute User user) {
        // 只能删除自己的视频
        videoService.deleteVideo(id, user.getId());
        return "redirect:/content/my-videos";
    }

    // 在 ProfileController.java 中添加查看其他用户主页的方法
    @GetMapping("/user/{userId}")
    public String userProfile(@PathVariable Integer userId,
                              @SessionAttribute User currentUser,
                              Model model) {
        model.addAttribute("currentUser", currentUser);

        // 这里应该通过 UserService 获取目标用户信息
        // 暂时用当前用户模拟
        if (userId.equals(currentUser.getId())) {
            // 如果是查看自己的主页，跳转到个人主页
            return "redirect:/profile/dashboard";
        }

        // 获取目标用户的文章和视频
        List<Article> userArticles = articleService.getUserArticles(userId);
        List<Video> userVideos = videoService.getUserVideos(userId);

        // 这里应该获取目标用户的用户名等信息
        // 暂时用占位数据
        model.addAttribute("targetUser", currentUser); // 应该替换为实际的目标用户
        model.addAttribute("userArticles", userArticles);
        model.addAttribute("userVideos", userVideos);
        model.addAttribute("articleCount", userArticles.size());
        model.addAttribute("videoCount", userVideos.size());

        return "profile/user-profile";
    }

    // 文件上传工具方法
    private String saveFile(MultipartFile file, String folder) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + fileExtension;

            String uploadDir = "uploads/" + folder + "/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = uploadDir + fileName;
            file.transferTo(new File(filePath));

            return "/" + filePath; // 返回相对路径
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}