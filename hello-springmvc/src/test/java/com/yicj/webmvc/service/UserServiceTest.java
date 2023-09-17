package com.yicj.webmvc.service;

import com.yicj.webmvc.WebMvcApplication;
import com.yicj.webmvc.anno.HelloAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collections;
import java.util.List;

/**
 * @author: yicj
 * @date: 2023/9/17 10:17
 */
@Slf4j
@SpringBootTest(classes = WebMvcApplication.class)
public class UserServiceTest {

    //@HelloAnnotation
    @Autowired
    @Qualifier
    private List<UserService> userServices = Collections.emptyList() ;

    @Test
    public void hello(){
        log.info("list size : {}", userServices.size());
        userServices.forEach(userService -> {
            String retValue = userService.hello();
            log.info("======> ret value : {}", retValue);
        });
    }


}
