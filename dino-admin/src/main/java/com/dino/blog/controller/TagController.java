package com.dino.blog.controller;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created 10-21-2022  6:45 PM
 * Author  Dino
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult getTagList(){
        return ResponseResult.okResult(tagService.list());
    }
}
