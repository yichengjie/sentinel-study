package com.yicj.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: yicj
 * @date: 2023/9/16 11:01
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(SentinelClientApplication.class, args) ;
    }
}
