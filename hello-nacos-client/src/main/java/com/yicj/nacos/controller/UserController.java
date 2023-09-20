package com.yicj.nacos.controller;

import com.yicj.common.model.vo.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @date 2023年09月20日 9:52
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/add")
    public RestResponse<String> userAdd(){

        return RestResponse.success("hello user add !") ;
    }

    @GetMapping("/delete")
    public RestResponse<String> userDelete(){

        return RestResponse.success("hello user delete !") ;
    }
}
