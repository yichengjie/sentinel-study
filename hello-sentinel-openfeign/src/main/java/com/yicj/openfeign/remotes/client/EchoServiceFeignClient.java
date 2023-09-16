package com.yicj.openfeign.remotes.client;

import com.yicj.openfeign.config.FeignConfiguration;
import com.yicj.openfeign.remotes.fallback.EchoServiceFeignClientFallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: yicj
 * @date: 2023/9/16 11:18
 */
@Qualifier("echoServiceFeignClient")
@FeignClient(name = "service-provider",
    fallback = EchoServiceFeignClientFallback.class,
    configuration = FeignConfiguration.class
)
public interface EchoServiceFeignClient {

    @GetMapping(value = "/echo/{str}")
    String echo(@PathVariable("str") String str);
}
