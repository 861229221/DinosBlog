package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.constants.SystemConstants;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.dto.ArticleDto;
import com.dino.blog.domain.entity.ArticleTag;
import com.dino.blog.domain.vo.*;
import com.dino.blog.domain.entity.Article;
import com.dino.blog.domain.entity.Category;
import com.dino.blog.mapper.ArticleMapper;
import com.dino.blog.service.ArticleService;
import com.dino.blog.service.ArticleTagService;
import com.dino.blog.service.CategoryService;
import com.dino.blog.service.TagService;
import com.dino.blog.utils.BeanCopyUtils;
import com.dino.blog.utils.RedisCache;
import com.dino.blog.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
        for (Article article : articles) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW_COUNT, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        //bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        for (Article article : articles) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW_COUNT, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);


        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW_COUNT, String.valueOf(id));
        articleDetailVo.setViewCount(viewCount.longValue());
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long articleId) {
        redisCache.incrementCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW_COUNT,articleId.toString());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult savaArticle(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        article.setCreateBy(SecurityUtil.getUserId());
        article.setCreateTime(new Date());
        article.setUpdateBy(SecurityUtil.getUserId());
        article.setUpdateTime(new Date());
        article.setViewCount(0L);
        save(article);
        List<Long> tags = articleDto.getTags();
        for (Long tag : tags) {
            articleTagService.save(new ArticleTag(article.getId(),tag));
        }
//        articleTagService
        return ResponseResult.okResult();
    }


//    @Override
//    public PageDto selectArticlePage(Article article, Integer pageNum, Integer pageSize) {
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
//
//        queryWrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle, article.getTitle());
//        queryWrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary, article.getSummary());
//
//        Page<Article> page = new Page<>();
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);
//        page(page,queryWrapper);
//
//        //转换成VO
//        List<Article> articles = page.getRecords();
//
//        //这里偷懒没写VO的转换 应该转换完在设置到最后的pageVo中
//
//        PageDto pageVo = new PageDto();
//        pageVo.setTotal(page.getTotal());
//        pageVo.setRows(articles);
//        return pageVo;
//    }

//    @Override
//    public ArticleVo getInfo(Long id) {
//        Article article = getById(id);
//        //获取关联标签
//        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
//        List<ArticleTag> articleTags = articleTagService.list(articleTagLambdaQueryWrapper);
//        List<Long> tags = articleTags.stream().map(articleTag -> articleTag.getTagId()).collect(Collectors.toList());
//
//        ArticleVo articleVo = BeanCopyUtils.copyBean(article,ArticleVo.class);
//        articleVo.setTags(tags);
//        return articleVo;
//    }
}
