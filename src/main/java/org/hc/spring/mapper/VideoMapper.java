// VideoMapper.java
package org.hc.spring.mapper;

import org.hc.spring.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface VideoMapper {
    int insertVideo(Video video);
    Video selectById(Integer id);
    List<Video> selectAll();
    List<Video> selectByUserId(Integer userId);
    int updateViewCount(Integer id);
    int updateLikeCount(@Param("id") Integer id, @Param("count") Integer count);
    int deleteVideo(Integer id);
}