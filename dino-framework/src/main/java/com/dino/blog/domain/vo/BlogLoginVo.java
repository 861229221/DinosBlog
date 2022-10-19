package com.dino.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created 10-19-2022  1:15 PM
 * Author  Dino
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogLoginVo {
    private String token;
    private UserInfoVo userInfo;
}
