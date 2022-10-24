package com.dino.blog.controller;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.dto.TagDto;
import com.dino.blog.domain.vo.PageVo;
import com.dino.blog.domain.vo.TagVo;
import com.dino.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created 10-21-2022  6:45 PM
 * Author  Dino
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult getTagList(@PathParam("pageNum") Long pageNum,
                                     @PathParam("pageSize") Long pageSize,
                                     @PathParam("name") String name) {
        PageVo tagList = tagService.getTagList(pageNum, pageSize, name);
        return ResponseResult.okResult(tagList);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDto tagDto) {
        return tagService.saveTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTags(@PathVariable String id ){
        String[] split = id.split(",");
        List<Long> list = Arrays.stream(split).map(new Function<String, Long>() {
            @Override
            public Long apply(String s) {
                return Long.valueOf(s);
            }
        }).collect(Collectors.toList());
        return tagService.deleteTags(list);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagDetails(@PathVariable String id){
        return tagService.getTagDetails(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagVo tagVo){
        return tagService.updateTag(tagVo);
    }

    @GetMapping("/listAllTag")
    public ResponseResult getAllTags(){
        return tagService.getAllTags();
    }
}
