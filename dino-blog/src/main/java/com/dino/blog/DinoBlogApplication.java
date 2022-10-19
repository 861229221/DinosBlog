package com.dino.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created 10-17-2022  7:46 PM
 * Author  Dino
 */
@SpringBootApplication
@MapperScan("com.dino.blog.mapper")
public class DinoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(DinoBlogApplication.class, args);
    }
}
