// ArticleService.java
package org.hc.spring.service;

import org.hc.spring.entity.Article;
import java.util.List;

// 在 ArticleService.java 接口中添加方法
public interface ArticleService {
    boolean publishArticle(Article article);
    Article getArticleById(Integer id);
    List<Article> getAllArticles();          // 所有用户的文章（公开）
    List<Article> getUserArticles(Integer userId);  // 指定用户的文章
    boolean deleteArticle(Integer id, Integer userId);
    void increaseViewCount(Integer id);

    // 新增统计方法
    int getUserArticleCount(Integer userId);
    int getUserTotalViews(Integer userId);
    int getUserTotalLikes(Integer userId);
}