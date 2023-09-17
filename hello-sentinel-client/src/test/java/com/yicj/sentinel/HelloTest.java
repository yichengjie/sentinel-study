package com.yicj.sentinel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

/**
 * @author: yicj
 * @date: 2023/9/17 21:31
 */
@SpringBootTest(classes = SentinelClientApplication.class)
public class HelloTest {

    private CircuitBreakerFactory circuitBreakerFactory ;

    @Test
    public void hello(){

        CircuitBreaker cb = circuitBreakerFactory.create("temp");
        cb.run(() -> {

            return 1 ;
        }) ;
    }

}
