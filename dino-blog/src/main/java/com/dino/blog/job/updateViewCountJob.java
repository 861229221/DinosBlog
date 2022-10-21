package com.dino.blog.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.dino.blog.constants.SystemConstants;
import com.dino.blog.domain.entity.Article;
import com.dino.blog.service.ArticleService;
import com.dino.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务，完成Redis和MySQL数据一致性
 * Created 10-21-2022  10:23 AM
 * Author  Dino
 */
@Component
public class updateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void doScheduledJob(){
        Map<String, Integer> redisViewCountMap = redisCache.getCacheMap(SystemConstants.REDIS_ARTICLE_VIEW_COUNT);
        Set<String> keySet = redisViewCountMap.keySet();
        List<Article> articleList = new ArrayList<>();
        for (String str : keySet) {
            Long articleId = Long.valueOf(str);
            Article article = articleService.getById(articleId);
            article.setViewCount(redisViewCountMap.get(str).longValue());
            articleList.add(article);
        }
        for (Article article : articleList) {
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article :: getId, article.getId());
            updateWrapper.set(Article :: getViewCount, article.getViewCount());
            articleService.update(updateWrapper);
        }
    }
}
