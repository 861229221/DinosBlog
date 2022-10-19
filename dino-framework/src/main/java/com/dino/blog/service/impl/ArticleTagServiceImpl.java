package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.domain.entity.ArticleTag;
import com.dino.blog.mapper.ArticleTagMapper;
import com.dino.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
