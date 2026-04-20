package org.hc.spring.service;

import org.hc.spring.entity.User;

public interface UserService {
    // 注册用户
    boolean register(User user);

    // 登录验证
    User login(String username, String password);

    // 检查用户名是否存在
    boolean isUsernameExists(String username);
}