package com.yicj.resttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
}
