package com.yicj.nacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author: yicj
 * @date: 2023/9/16 10:05
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosClientApplication.class, args) ;
    }


    @Component
    static class EventReceiver implements ApplicationListener<EnvironmentChangeEvent>{

        @Override
        public void onApplicationEvent(EnvironmentChangeEvent event) {
            log.info("=======> event keys : {}", event.getKeys());
        }
    }
}
