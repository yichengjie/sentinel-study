package com.yicj.resttemplate.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.yicj.resttemplate.utils.ExceptionUtil;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: yicj
 * @date: 2023/9/16 11:44
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(fallbackClass = ExceptionUtil.class,fallback = "fallBack",
            blockHandlerClass = ExceptionUtil.class,blockHandler = "handleBlock")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
