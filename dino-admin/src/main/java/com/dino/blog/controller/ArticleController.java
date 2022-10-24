package com.dino.blog.controller;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.dto.ArticleDto;
import com.dino.blog.domain.vo.ArticleVo;
import com.dino.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;

/**
 * Created 10-24-2022  3:17 PM
 * Author  Dino
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult savaArticle(@RequestBody ArticleDto articleDto){
        return articleService.savaArticle(articleDto);
    }
}
