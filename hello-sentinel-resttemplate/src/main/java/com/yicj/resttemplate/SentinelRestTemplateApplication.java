package com.yicj.resttemplate;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.yicj.resttemplate.utils.ExceptionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author: yicj
 * @date: 2023/9/16 11:57
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelRestTemplateApplication {

    public static void main(String[] args) {

        SpringApplication.run(SentinelRestTemplateApplication.class, args) ;
    }

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(
            fallbackClass = ExceptionUtil.class,fallback = "fallBack",
            blockHandlerClass = ExceptionUtil.class, blockHandler = "handleBlock")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
