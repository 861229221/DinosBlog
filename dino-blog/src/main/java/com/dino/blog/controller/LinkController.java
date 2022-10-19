package com.dino.blog.controller;

import com.dino.blog.domain.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created 10-18-2022  9:10 PM
 * Author  Dino
 */
@RestController
@RequestMapping("/link")
public class LinkController {


    @GetMapping("/getAllLink")
    public ResponseResult getAllLink() {
        return null;
    }
}
