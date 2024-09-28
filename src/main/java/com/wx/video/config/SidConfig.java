package com.wx.video.config;

import org.n3r.idworker.Sid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description 自动计数器的配置
 */
@Configuration
public class SidConfig {

    @Bean
    public Sid getSid() {
        return new Sid();
    }

}
