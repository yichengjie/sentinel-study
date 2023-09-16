package com.yicj.openfeign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yicj
 * @date: 2023/9/16 11:20
 */
@Configuration
public class FeignConfiguration {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }
}
