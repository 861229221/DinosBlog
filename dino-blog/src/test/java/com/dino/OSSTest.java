package com.dino;

import com.dino.blog.DinoBlogApplication;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

/**
 * Created 10-20-2022  2:57 PM
 * Author  Dino
 */
@SpringBootTest(classes = DinoBlogApplication.class)
public class OSSTest {
    @Test
    public void addTest() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "dVgUqyW0Ju-hkN-9wVw-yGwrRfhb9DJnQATBjag0";
        String secretKey = "u94svu3oXpgMStdWfWmbvycethb2_XsseK8G2UPz";
        String bucket = "donalddino";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            FileInputStream fis = new FileInputStream("/Users/dino/Desktop/IMG_3980.JPG");

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fis, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
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
        }

    }
}
