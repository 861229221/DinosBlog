package com.dino.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created 10-19-2022  11:00 AM
 * Author  Dino
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
}
