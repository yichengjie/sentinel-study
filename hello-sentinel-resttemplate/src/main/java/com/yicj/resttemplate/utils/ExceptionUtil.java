package com.yicj.resttemplate.utils;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.yicj.common.model.vo.RestResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;


public class ExceptionUtil {

    public static ClientHttpResponse handleBlock(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException be){
        RestResponse<Void> commonResult =  RestResponse.error("500","降级处理函数。。。。。");
        return new SentinelClientHttpResponse(JSON.toJSONString(commonResult));
    }

    public static ClientHttpResponse fallBack(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException be){
        RestResponse<Void> commonResult = RestResponse.error("500","异常处理函数。。。。。");
        return new SentinelClientHttpResponse(JSON.toJSONString(commonResult));
    }
}