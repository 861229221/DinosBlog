package com.dino.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created 10-24-2022  1:51 PM
 * Author  Dino
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private Long id;

    //标签名
    private String name;


    //备注
    private String remark;
}
