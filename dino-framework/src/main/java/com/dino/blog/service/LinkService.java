package com.dino.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author dino
 * @since 2022-10-19 10:54:56
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
