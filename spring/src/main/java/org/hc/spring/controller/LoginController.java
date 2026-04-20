package org.hc.spring.controller;

import org.hc.spring.entity.User;
import org.hc.spring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes("user")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("welcomeMessage", "欢迎使用内容分享平台");
        model.addAttribute("currentTime", java.time.LocalDateTime.now());
        return "index";
    }

    @GetMapping("/home")
    public String home(@SessionAttribute(name = "user", required = false) User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("welcomeMessage", "欢迎回来，" + user.getUsername() + "！");
        model.addAttribute("currentTime", java.time.LocalDateTime.now());
        model.addAttribute("systemStatus", "系统运行正常");
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(@SessionAttribute(name = "user", required = false) User user) {
        // 如果已经登录，直接跳转到首页
        if (user != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        try {
            logger.info("尝试登录用户: {}", username);
            User user = userService.login(username, password);
            if (user != null) {
                logger.info("用户 {} 登录成功", username);
                model.addAttribute("user", user);
                return "redirect:/home";
            } else {
                logger.warn("用户 {} 登录失败", username);
                model.addAttribute("error", "用户名或密码错误");
                return "login";
            }
        } catch (Exception e) {
            logger.error("登录过程中发生错误", e);
            model.addAttribute("error", "系统错误，请稍后重试");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage(@SessionAttribute(name = "user", required = false) User user) {
        if (user != null) {
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           Model model) {
        try {
            if (userService.isUsernameExists(username)) {
                model.addAttribute("error", "用户名已存在");
                return "register";
            }

            User user = new User(username, password, email);
            boolean success = userService.register(user);

            if (success) {
                model.addAttribute("message", "注册成功，请登录");
                return "login";
            } else {
                model.addAttribute("error", "注册失败，请重试");
                return "register";
            }
        } catch (Exception e) {
            logger.error("注册过程中发生错误", e);
            model.addAttribute("error", "系统错误，请稍后重试");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }
}