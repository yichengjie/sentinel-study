package com.yicj.openfeign;

import com.yicj.openfeign.remotes.client.EchoServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yicj
 * @date: 2023/9/16 11:16
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelFeignApplication {

    public static void main(String[] args) {

        SpringApplication.run(SentinelFeignApplication.class, args) ;
    }


    @RestController
    class TestController{

        @Autowired
        @Qualifier("echoServiceFeignClient")
        private EchoServiceFeignClient feignClient ;


        @GetMapping("/echo/{value}")
        public String echo(@PathVariable String value){
            return feignClient.echo(value) ;
        }
    }
}
