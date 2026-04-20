// ProfileController.java - 修复统计逻辑
package org.hc.spring.controller;

import org.hc.spring.entity.User;
import org.hc.spring.service.ArticleService;
import org.hc.spring.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ArticleService articleService;
    private final VideoService videoService;

    public ProfileController(ArticleService articleService, VideoService videoService) {
        this.articleService = articleService;
        this.videoService = videoService;
    }

    // 个人主页 - 只显示当前用户的数据
    @GetMapping("/dashboard")
    public String dashboard(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);

        // 获取当前用户的文章和视频统计
        int articleCount = articleService.getUserArticleCount(user.getId());
        int videoCount = videoService.getUserVideoCount(user.getId());

        // 计算当前用户的总浏览量和点赞数
        int articleViews = articleService.getUserTotalViews(user.getId());
        int videoViews = videoService.getUserTotalViews(user.getId());
        int totalViews = articleViews + videoViews;

        int articleLikes = articleService.getUserTotalLikes(user.getId());
        int videoLikes = videoService.getUserTotalLikes(user.getId());
        int totalLikes = articleLikes + videoLikes;

        model.addAttribute("articleCount", articleCount);
        model.addAttribute("videoCount", videoCount);
        model.addAttribute("totalViews", totalViews);
        model.addAttribute("totalLikes", totalLikes);

        return "profile/dashboard";
    }

    // 个人信息 - 只显示当前用户信息
    @GetMapping("/info")
    public String profileInfo(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "profile/info";
    }

    // 我的收藏 - 当前用户的收藏
    @GetMapping("/favorites")
    public String favorites(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "profile/favorites";
    }

    // 消息通知 - 当前用户的通知
    @GetMapping("/notifications")
    public String notifications(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "profile/notifications";
    }

    // 设置 - 当前用户的设置
    @GetMapping("/settings")
    public String settings(@SessionAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "profile/settings";
    }
}