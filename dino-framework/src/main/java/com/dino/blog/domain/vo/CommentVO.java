package com.dino.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created 10-19-2022  4:37 PM
 * Author  Dino
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    private Long id;

    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;

    private String toCommentUserName;

    private Long createBy;

    private Date createTime;

    private String username;

    private List<CommentVO> children;

}
