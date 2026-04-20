// ArticleMapper.java
package org.hc.spring.mapper;

import org.hc.spring.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ArticleMapper {
    int insertArticle(Article article);
    Article selectById(Integer id);
    List<Article> selectAll();
    List<Article> selectByUserId(Integer userId);
    int updateViewCount(Integer id);
    int updateLikeCount(@Param("id") Integer id, @Param("count") Integer count);
    int deleteArticle(Integer id);
}