package com.yicj.resttemplate.controller;

import com.alibaba.fastjson.TypeReference;
import com.yicj.common.model.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @author: yicj
 * @date: 2023/9/16 12:16
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate ;

    @GetMapping("/index")
    public RestResponse<String> index(){
        String url = "http://hello-nacos-client/hello/index" ;
        RestResponse<String> retValue = restTemplate.getForObject(url, RestResponse.class);
        log.info("ret value : {}", retValue);
        return retValue ;
    }

    @GetMapping("/exception")
    public Object exception(){
        String url = "http://hello-nacos-client/hello/exception" ;
        //TypeReference<RestResponse<String>> typeReference = new TypeReference<>() ;
        ParameterizedTypeReference<RestResponse<String>> typeReference = new ParameterizedTypeReference<>() {};
        ResponseEntity<RestResponse<String>> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), typeReference);
        RestResponse<String> retValue = exchange.getBody();
        log.info("ret value : {}", retValue);
        return retValue ;
    }
}
