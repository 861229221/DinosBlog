package com.dino.blog.controller;

import com.dino.blog.annotation.SystemLog;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.vo.CategoryVo;
import com.dino.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created 10-18-2022  6:19 PM
 * Author  Dino
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult getAllCategory() {
        List<CategoryVo> categoryVoList = categoryService.listAllCategory();
        return ResponseResult.okResult(categoryVoList);
    }
}
