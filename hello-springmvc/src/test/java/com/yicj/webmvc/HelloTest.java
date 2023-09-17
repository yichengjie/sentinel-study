package com.yicj.webmvc;

import com.yicj.webmvc.anno.HelloAnnotation;
import com.yicj.webmvc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

/**
 * @author: yicj
 * @date: 2023/9/17 10:16
 */
@Slf4j
@SpringBootTest(classes = WebMvcApplication.class)
public class HelloTest {

    @Autowired
    //@HelloAnnotation
    @Qualifier
    private List<UserService> userServices = Collections.emptyList();
    //private UserService userService ;

    @Test
    public void hello(){
        log.info("====> list size : {}", userServices.size());
    }

}
