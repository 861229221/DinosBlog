package com.dino.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dino.blog.domain.entity.Tag;
import org.springframework.stereotype.Repository;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author dino
 * @since 2022-10-21 17:21:53
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

}

