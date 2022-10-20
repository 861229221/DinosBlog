package com.dino.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created 10-20-2022  4:56 PM
 * Author  Dino
 */
public class PathUtils {
    public static String generateFilePath(String fileName){
        //根据日期生成路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }
}