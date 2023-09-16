package com.yicj.resttemplate.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.yicj.common.model.vo.RestResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

/**
 * @author: yicj
 * @date: 2023/9/16 14:11
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(
            fallbackClass = ExceptionUtil.class,fallback = "fallBack",
            blockHandlerClass = ExceptionUtil.class, blockHandler = "handleBlock")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static class ExceptionUtil {
        public static ClientHttpResponse handleBlock(
                HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException be){
            RestResponse<Void> commonResult =  RestResponse.error("500","降级处理函数 block 。。。。。");
            return new SentinelClientHttpResponse(JSON.toJSONString(commonResult));
        }

        public static ClientHttpResponse fallBack(
                HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException be){
            RestResponse<Void> commonResult = RestResponse.error("500","异常处理函数 fallback 。。。。。");
            return new SentinelClientHttpResponse(JSON.toJSONString(commonResult));
        }
    }
}
