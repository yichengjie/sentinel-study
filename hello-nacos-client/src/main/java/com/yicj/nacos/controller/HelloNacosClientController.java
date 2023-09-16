package com.yicj.nacos.controller;

import com.yicj.common.model.vo.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yicj
 * @date: 2023/9/16 12:12
 */
@RestController
@RequestMapping("/hello")
public class HelloNacosClientController {

    @GetMapping("/index")
    public RestResponse<String> index(){

        return RestResponse.success("hello world index !") ;
    }


    @GetMapping("/exception")
    public RestResponse<String> exception(){
        int a = 1/0 ;
        return RestResponse.success("hello exception !") ;
    }

}
