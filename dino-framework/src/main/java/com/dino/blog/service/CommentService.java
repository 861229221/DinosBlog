package com.dino.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author dino
 * @since 2022-10-19 16:12:34
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Integer pageNum, Integer pageSize, Integer articleId);

    ResponseResult addComment(Comment comment);

    ResponseResult getLinkCommentList(Integer pageNum, Integer pageSize);
}
