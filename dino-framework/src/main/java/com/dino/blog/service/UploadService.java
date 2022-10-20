package com.dino.blog.service;

import com.dino.blog.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created 10-20-2022  3:56 PM
 * Author  Dino
 */
public interface UploadService {
    ResponseResult upload(MultipartFile img);
}
