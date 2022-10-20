package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.constants.SystemConstants;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.Comment;
import com.dino.blog.domain.vo.CommentVO;
import com.dino.blog.domain.vo.PageVo;
import com.dino.blog.enums.AppHttpCodeEnum;
import com.dino.blog.exception.SystemException;
import com.dino.blog.mapper.CommentMapper;
import com.dino.blog.service.CommentService;
import com.dino.blog.service.UserService;
import com.dino.blog.utils.BeanCopyUtils;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 评论表(Comment)表服务实现类
 *
 * @author dino
 * @since 2022-10-19 16:12:34
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Integer pageNum, Integer pageSize, Integer articleId) {
        // 查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_COMMENT_ID);
        // 分页查询
        Page<Comment> page = new Page(pageNum, pageSize);
        page = page(page, queryWrapper);
        List<CommentVO> commentVOList = toCommentVOList(page.getRecords());

        // 查询所有根评论对应的子评论
        for (CommentVO commentVO : commentVOList) {
            List<CommentVO> children = getChildrenComments(commentVO.getId());
            commentVO.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVOList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (StringUtils.isNullOrEmpty(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.COMMENT_IS_NULL);
        }
        // MyMetaObjectHandler类字段自动填充
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询子评论
     *
     * @param id
     * @return
     */
    private List<CommentVO> getChildrenComments(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        return toCommentVOList(list);
    }

    /**
     * 封装返回数据，并查询用户昵称
     * @param list
     * @return
     */
    private List<CommentVO> toCommentVOList(List<Comment> list) {
        List<CommentVO> commentVOList = BeanCopyUtils.copyBeanList(list, CommentVO.class);
        // 处理缺少的数据
        for (CommentVO commentVO : commentVOList) {
            // 查询用户昵称并赋值
            String nickName = userService.getById(commentVO.getCreateBy()).getNickName();
            commentVO.setUsername(nickName);
            // 通过toCommentUserId查询昵称并赋值
            if (commentVO.getToCommentUserId() != -1) {
                String toUserName = userService.getById(commentVO.getToCommentUserId()).getNickName();
                commentVO.setToCommentUserName(toUserName);
            }
        }
        return commentVOList;
    }


    @Override
    public ResponseResult getLinkCommentList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getType,SystemConstants.LINK_COMMENT);
        queryWrapper.eq(Comment::getRootId,SystemConstants.ROOT_COMMENT_ID);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        // 分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<CommentVO> commentVOList = toCommentVOList(page.getRecords());
        for (CommentVO commentVO : commentVOList) {
            Long id = commentVO.getId();
            List<CommentVO> childrenComments = getChildrenComments(id);
            commentVO.setChildren(childrenComments);
        }
        return ResponseResult.okResult(new PageVo(commentVOList,page.getTotal()));
    }
}
