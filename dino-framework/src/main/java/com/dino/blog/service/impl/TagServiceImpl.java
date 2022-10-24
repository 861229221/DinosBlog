package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.dto.TagDto;
import com.dino.blog.domain.entity.Tag;
import com.dino.blog.domain.vo.PageVo;
import com.dino.blog.domain.vo.TagVo;
import com.dino.blog.mapper.TagMapper;
import com.dino.blog.service.TagService;
import com.dino.blog.utils.BeanCopyUtils;
import com.dino.blog.utils.SecurityUtil;
import com.qiniu.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author dino
 * @since 2022-10-21 17:21:55
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public PageVo getTagList(Long pageNum, Long pageSize, String name) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(name)) {
            queryWrapper.like(Tag::getName, name);
        }
        Page<Tag> page = new Page(pageNum, pageSize);
        List<Tag> tagList = page(page, queryWrapper).getRecords();
        PageVo pageVo = new PageVo();
        pageVo.setRows(tagList);
        pageVo.setTotal(page.getTotal());
        return pageVo;
    }

    @Override
    public ResponseResult saveTag(TagDto tagDto) {
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        tag.setCreateBy(SecurityUtil.getUserId());
        tag.setUpdateBy(SecurityUtil.getUserId());
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTags(List<Long> list) {
        removeByIds(list);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagDetails(String id) {
        TagVo tagVo = BeanCopyUtils.copyBean(getById(id), TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(TagVo tagVo) {
        Tag tag = getById(tagVo.getId());
        tag.setUpdateBy(SecurityUtil.getUserId());
        tag.setUpdateTime(new Date());
        tag.setName(tagVo.getName());
        tag.setRemark(tagVo.getRemark());
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAllTags() {
        List<Tag> list = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

}
