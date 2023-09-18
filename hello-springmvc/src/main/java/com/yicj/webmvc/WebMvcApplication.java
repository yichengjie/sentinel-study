package com.yicj.webmvc;

import com.yicj.webmvc.properties.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author: yicj
 * @date: 2023/9/17 10:13
 */
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(CustomProperties.class)
public class WebMvcApplication implements ApplicationRunner {

    @Autowired
    private CustomProperties customProperties ;

    public static void main(String[] args) {

        SpringApplication.run(WebMvcApplication.class, args) ;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("user list size : {}", customProperties.getUsers().size());
        customProperties.getUsers()
                .forEach(item -> log.info("user item : {}", item));
    }
}
