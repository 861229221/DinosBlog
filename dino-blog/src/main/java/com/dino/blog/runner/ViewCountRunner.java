package com.dino.blog.runner;

import com.dino.blog.domain.entity.Article;
import com.dino.blog.mapper.ArticleMapper;
import com.dino.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.dino.blog.constants.SystemConstants.REDIS_ARTICLE_VIEW_COUNT;


/**
 * Created 10-21-2022  10:21 AM
 * Author  Dino
 */
@Component
public class ViewCountRunner implements org.springframework.boot.CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息 key:id  value:viewCount
        List<Article> articleList = articleMapper.selectList(null);
        Map<String, Integer> collect = articleList.stream()
                .collect(Collectors.toMap(new Function<Article, String>() {
                    @Override
                    public String apply(Article article) {
                        return article.getId().toString();
                    }
                }, new Function<Article, Integer>() {
                    @Override
                    public Integer apply(Article article) {
                        return article.getViewCount().intValue();
                    }
                }));
        redisCache.setCacheMap(REDIS_ARTICLE_VIEW_COUNT,collect);
    }
}
