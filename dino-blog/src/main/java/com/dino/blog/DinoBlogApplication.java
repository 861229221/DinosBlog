package com.dino.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created 10-17-2022  7:46 PM
 * Author  Dino
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@MapperScan("com.dino.blog.mapper")
public class DinoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(DinoBlogApplication.class, args);
    }
}
