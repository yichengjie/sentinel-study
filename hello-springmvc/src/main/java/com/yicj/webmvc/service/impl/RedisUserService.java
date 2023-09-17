package com.yicj.webmvc.service.impl;

import com.yicj.webmvc.anno.HelloAnnotation;
import com.yicj.webmvc.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: yicj
 * @date: 2023/9/17 10:15
 */
@Service
public class RedisUserService implements UserService {
    @Override
    public String hello() {
        return "redis hello world";
    }
}
