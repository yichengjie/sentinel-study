package com.yicj.openfeign.remotes.fallback;

import com.yicj.openfeign.remotes.client.EchoServiceFeignClient;

/**
 * @author: yicj
 * @date: 2023/9/16 11:19
 */
public class EchoServiceFeignClientFallback implements EchoServiceFeignClient {
    @Override
    public String dynamic(String str) {
        return "dynaic echo fallback " + str;
    }

    @Override
    public String fixed() {
        return "echo fixed fallback !";
    }

}
