package com.dino.blog.service.impl;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.enums.AppHttpCodeEnum;
import com.dino.blog.exception.SystemException;
import com.dino.blog.service.UploadService;
import com.dino.blog.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created 10-20-2022  3:56 PM
 * Author  Dino
 */
@Service
@Setter
public class UploadServiceImpl implements UploadService {

    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.secretKey}")
    private String secretKey;
    @Value("${oss.bucket}")
    private String bucket;
    @Value("${oss.urlPrefix}")
    private String urlPrefix;

    @Override
    public ResponseResult upload(MultipartFile img) {
        // 判断文件大小和类型
        if (!img.getOriginalFilename().endsWith(".png") && !img.getOriginalFilename().endsWith(".JPG")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传


        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = PathUtils.generateFilePath(img.getOriginalFilename());

        try {
            InputStream fis = img.getInputStream();

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fis, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
                System.err.println(urlPrefix + key);
                return ResponseResult.okResult(urlPrefix + key);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
