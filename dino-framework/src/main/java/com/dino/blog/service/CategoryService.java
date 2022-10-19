package com.dino.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.vo.CategoryVo;
import com.dino.blog.domain.entity.Category;

import java.util.List;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-02-02 12:29:50
 */
public interface CategoryService extends IService<Category> {


    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

}

