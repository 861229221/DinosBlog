package com.dino.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long articleId);

}
