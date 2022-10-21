package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.domain.entity.Tag;
import com.dino.blog.mapper.TagMapper;
import com.dino.blog.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author dino
 * @since 2022-10-21 17:21:55
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
