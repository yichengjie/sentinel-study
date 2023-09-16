package com.yicj.openfeign.config;

import com.yicj.openfeign.remotes.client.EchoServiceFeignClient;
import com.yicj.openfeign.remotes.fallback.EchoServiceFeignClientFallback;
import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author: yicj
 * @date: 2023/9/16 11:20
 */
public class FeignConfiguration {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public EchoServiceFeignClient echoServiceFallback() {
        return new EchoServiceFeignClientFallback();
    }
}
