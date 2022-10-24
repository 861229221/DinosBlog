package com.dino.blog.controller;

import com.dino.blog.annotation.SystemLog;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created 10-20-2022  3:51 PM
 * Author  Dino
 */
@Controller
@RequestMapping
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @ResponseBody
    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        return uploadService.upload(multipartFile);
    }
}
