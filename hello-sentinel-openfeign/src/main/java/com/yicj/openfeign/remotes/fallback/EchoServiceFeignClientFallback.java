package com.yicj.openfeign.remotes.fallback;

import com.yicj.openfeign.remotes.client.EchoServiceFeignClient;
import org.springframework.stereotype.Component;

/**
 * @author: yicj
 * @date: 2023/9/16 11:19
 */
public class EchoServiceFeignClientFallback implements EchoServiceFeignClient {

    @Override
    public String echo(String str) {
        return "echo fallback";
    }
}
