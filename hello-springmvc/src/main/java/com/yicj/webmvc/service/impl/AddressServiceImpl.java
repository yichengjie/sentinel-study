package com.yicj.webmvc.service.impl;

import com.yicj.webmvc.anno.HelloAnnotation;
import com.yicj.webmvc.service.AddressService;
import com.yicj.webmvc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/9/17 17:52
 */
@Slf4j
@Service("addressServiceImpl")
public class AddressServiceImpl implements AddressService {

    @Autowired
    @Qualifier
    private List<UserService> userServices ;

    @Override
    public String execute(String userId) {
        return userId + " address info !";
    }

    @Override
    public String hello() {
        log.info("service size : {}", userServices.size());
        UserService userService = userServices.get(0);
        return userService.hello();
    }
}
