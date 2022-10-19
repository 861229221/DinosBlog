package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.constants.SystemConstants;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.dto.LinkDto;
import com.dino.blog.domain.entity.Link;
import com.dino.blog.mapper.LinkMapper;
import com.dino.blog.service.LinkService;
import com.dino.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author dino
 * @since 2022-10-19 10:54:59
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        List<LinkDto> linkDtoList = BeanCopyUtils.copyBeanList(list, LinkDto.class);
        return ResponseResult.okResult(linkDtoList);
    }
}
