package com.yicj.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/hello")
    @SentinelResource(value = "test_hello", blockHandler = "blockHandlerHello")
    public String hello() {
        return "Hello Sentinel";
    }


    @GetMapping(value = "/exception")
    @SentinelResource(value = "test_exception", fallback = "fallbackHello")
    public String exception(@RequestParam(value = "param", required = false) String param) {
        if (StringUtils.hasText(param)){
            int i = 1/0 ;
        }
        return "Hello Sentinel";
    }

    public String blockHandlerHello(BlockException e){
        log.error("被限流了异常打印：", e);
        return "被限流了" ;
    }

    public String fallbackHello(String param, Throwable e){
        log.error("被降级异常打印：", e);
        return "被降级了" ;
    }

}