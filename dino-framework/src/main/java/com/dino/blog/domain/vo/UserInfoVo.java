package com.dino.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created 10-19-2022  1:16 PM
 * Author  Dino
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoVo {
    private Long id;
    private String nickName;
    // 头像
    private String avatar;
    private String sex;
    private String email;
}
