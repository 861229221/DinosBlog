package com.dino.blog.controller;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.Comment;
import com.dino.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created 10-19-2022  4:16 PM
 * Author  Dino
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult getCommentList(@PathParam("pageNum") Integer pageNum,
                                         @PathParam("pageSize") Integer pageSize,
                                         @PathParam("articleId") Integer articleId
    ) {
        return commentService.commentList(pageNum, pageSize, articleId);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult getLinkCommentList(@PathParam("pageNum") Integer pageNum,
                                             @PathParam("pageSize") Integer pageSize) {
        return commentService.getLinkCommentList(pageNum,pageSize);
    }
}
