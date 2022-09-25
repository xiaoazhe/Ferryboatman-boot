package com.ferry.web;


import com.ferry.core.file.util.IdWorker;
import com.ferry.web.util.JwtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: 摆渡人
 * @Date: 2021/4/27
 */
@SpringBootApplication
@MapperScan({"com.ferry.core.*.mapper", "com.ferry.server.*.mapper"})
@EnableMongoRepositories(value= "com.ferry.server.blog.db")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public IdWorker idWorkker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
}