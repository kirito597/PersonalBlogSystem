// BaseController.java
package org.hc.spring.controller;

import org.hc.spring.entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

public class BaseController {

    /**
     * 检查用户是否登录，如果未登录则重定向到登录页面
     */
    protected String checkLogin(@SessionAttribute(name = "user", required = false) User user) {
        if (user == null) {
            return "redirect:/login";
        }
        return null;
    }

    /**
     * 添加用户信息到模型
     */
    @ModelAttribute
    protected void addUserToModel(@SessionAttribute(name = "user", required = false) User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
    }
}