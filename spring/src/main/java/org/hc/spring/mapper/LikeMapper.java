// LikeMapper.java
package org.hc.spring.mapper;

import org.hc.spring.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    int insertLike(Like like);
    int deleteLike(@Param("userId") Integer userId, @Param("targetType") Integer targetType, @Param("targetId") Integer targetId);
    Like selectByUserAndTarget(@Param("userId") Integer userId, @Param("targetType") Integer targetType, @Param("targetId") Integer targetId);
    int countLikesByTarget(@Param("targetType") Integer targetType, @Param("targetId") Integer targetId);
}