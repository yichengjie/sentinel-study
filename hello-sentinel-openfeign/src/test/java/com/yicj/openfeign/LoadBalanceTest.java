package com.yicj.openfeign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/9/17 8:31
 */
@SpringBootTest(classes = SentinelFeignApplication.class)
public class LoadBalanceTest {

    @Autowired
    private DiscoveryClient discoveryClient ;

    @Test
    public void hello(){

        List<ServiceInstance> instances = discoveryClient.getInstances("my-service");
        //  LoadBalancerClient client = new BlockingLoadBalancerClient(instances) ;

    }
}
