package com.dino.blog.controller;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;

/**
 * Created 10-20-2022  3:51 PM
 * Author  Dino
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping
    public ResponseResult upload(@RequestBody MultipartFile img) {
        return uploadService.upload(img);
    }
}
