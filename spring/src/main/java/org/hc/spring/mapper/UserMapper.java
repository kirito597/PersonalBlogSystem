package org.hc.spring.mapper;

import org.hc.spring.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 插入用户
    int insertUser(User user);

    // 根据用户名查询用户
    User selectByUsername(String username);

    // 根据用户名和密码查询用户
    User selectByUsernameAndPassword(String username, String password);
}