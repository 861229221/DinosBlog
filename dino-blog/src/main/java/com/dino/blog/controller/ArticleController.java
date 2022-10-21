package com.dino.blog.controller;

import com.dino.blog.annotation.SystemLog;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "获取热点文章")
    public ResponseResult hotArticleList(){

        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "获取文章集合")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取文章详细信息")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{articleId}")
    @SystemLog(businessName = "增加浏览次数")
    public ResponseResult updateViewCount(@PathVariable("articleId") Long articleId){
        return articleService.updateViewCount(articleId);
    }
}
