package com.dino.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.dto.TagDto;
import com.dino.blog.domain.entity.Tag;
import com.dino.blog.domain.vo.PageVo;
import com.dino.blog.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author dino
 * @since 2022-10-21 17:21:55
 */
public interface TagService extends IService<Tag> {

    PageVo getTagList(Long pageNum, Long pageSize, String name);

    ResponseResult saveTag(TagDto tagDto);

    ResponseResult deleteTags(List<Long> list);

    ResponseResult getTagDetails(String id);

    ResponseResult updateTag(TagVo tagVo);

    ResponseResult getAllTags();

}
