// ArticleServiceImpl.java
package org.hc.spring.service.impl;

import org.hc.spring.entity.Article;
import org.hc.spring.mapper.ArticleMapper;
import org.hc.spring.service.ArticleService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public boolean publishArticle(Article article) {
        return articleMapper.insertArticle(article) > 0;
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleMapper.selectById(id);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleMapper.selectAll();
    }

    @Override
    public List<Article> getUserArticles(Integer userId) {
        return articleMapper.selectByUserId(userId);
    }

    // 在 ArticleServiceImpl.java 中修复 deleteArticle 方法
    @Override
    public boolean deleteArticle(Integer id, Integer userId) {
        Article article = articleMapper.selectById(id);
        // 只能删除自己的文章
        if (article != null && article.getUserId().equals(userId)) {
            return articleMapper.deleteArticle(id) > 0;
        }
        return false;
    }

    @Override
    public void increaseViewCount(Integer id) {
        articleMapper.updateViewCount(id);
    }

    // 在 ArticleServiceImpl.java 中添加方法
    @Override
    public int getUserArticleCount(Integer userId) {
        return getUserArticles(userId).size();
    }

    @Override
    public int getUserTotalViews(Integer userId) {
        return getUserArticles(userId).stream()
                .mapToInt(article -> article.getViewCount() != null ? article.getViewCount() : 0)
                .sum();
    }

    @Override
    public int getUserTotalLikes(Integer userId) {
        return getUserArticles(userId).stream()
                .mapToInt(article -> article.getLikeCount() != null ? article.getLikeCount() : 0)
                .sum();
    }
}