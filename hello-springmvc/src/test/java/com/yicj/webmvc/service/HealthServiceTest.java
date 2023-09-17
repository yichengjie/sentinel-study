package com.yicj.webmvc.service;

import com.yicj.webmvc.service.impl.DefaultUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: yicj
 * @date: 2023/9/17 17:16
 */
@Slf4j
public class HealthServiceTest {

    @Test
    public void execute(){
        UserService userService = new DefaultUserService() ;

        Object proxy = Proxy.newProxyInstance(
                HealthServiceTest.class.getClassLoader(),
                new Class[]{UserService.class, HealthService.class},
                new HealthInvocationHandler(userService)
        );
        log.info("ret value : {}", ((UserService)proxy).hello());
        log.info("-------------------------------");
        log.info("ret value : {}", ((HealthService)proxy).execute());
    }

    static class HealthInvocationHandler implements InvocationHandler{

        private Object target ;

        public HealthInvocationHandler(Object target){
            this.target = target ;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            log.info("method name : {}, class name: {}", methodName, method.getDeclaringClass().getName());
            if ("execute".equals(methodName)
                    && method.getDeclaringClass().isAssignableFrom(HealthService.class)){
                log.info("health check method execute !");
                return "up" ;
            }
            return method.invoke(target, args) ;
        }
    }
}
