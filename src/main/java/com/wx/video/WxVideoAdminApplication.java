package com.wx.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.wx.video.mapper")
@EnableCaching  // 开启缓存
public class WxVideoAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxVideoAdminApplication.class, args);
    }

}

