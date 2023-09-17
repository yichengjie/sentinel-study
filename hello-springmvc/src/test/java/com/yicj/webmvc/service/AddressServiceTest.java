package com.yicj.webmvc.service;

import com.yicj.webmvc.WebMvcApplication;
import com.yicj.webmvc.service.impl.AddressServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * @author: yicj
 * @date: 2023/9/17 17:56
 */
@Slf4j
@SpringBootTest(classes = WebMvcApplication.class)
public class AddressServiceTest {

    @Autowired
    private ApplicationContext applicationContext ;

    @Autowired
    private AddressService addressService ;

    @Test
    public void hello(){
        //AddressService addressService = new AddressServiceImpl() ;
        String beanName = "addressServiceImpl" ;
        this.applicationContext.getAutowireCapableBeanFactory().destroyBean(addressService);
        //
        this.applicationContext.getAutowireCapableBeanFactory()
                .initializeBean(addressService, beanName);
        String retValue = addressService.hello();
        log.info("ret value : {}", retValue);
    }

}
