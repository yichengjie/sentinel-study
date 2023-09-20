package com.yicj.gateway;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: yicj
 * @date: 2023/9/16 9:15
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelGatewayApplication {
    public static void main(String[] args) {
        System.setProperty(SentinelConfig.APP_TYPE_PROP_KEY, "1");
        System.setProperty("csp.sentinel.dashboard.server","localhost:8858");
        System.setProperty(SentinelConfig.PROJECT_NAME_PROP_KEY,"hello-sentinel-gateway");
        // 以上参数需要配置
        SpringApplication.run(SentinelGatewayApplication.class, args) ;
    }
}
