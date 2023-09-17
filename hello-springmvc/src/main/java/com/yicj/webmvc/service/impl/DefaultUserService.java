package com.yicj.webmvc.service.impl;

import com.yicj.webmvc.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: yicj
 * @date: 2023/9/17 10:14
 */
@Service
public class DefaultUserService implements UserService {
    @Override
    public String hello() {

        return "default hello world";
    }
}
