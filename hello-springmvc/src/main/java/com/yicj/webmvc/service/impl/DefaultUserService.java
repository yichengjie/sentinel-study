package com.yicj.webmvc.service.impl;

import com.yicj.webmvc.anno.HelloAnnotation;
import com.yicj.webmvc.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author: yicj
 * @date: 2023/9/17 10:14
 */
@Service
@Qualifier
//@HelloAnnotation
public class DefaultUserService implements UserService {

    @Override
    public String hello() {

        return "default hello world";
    }
}
