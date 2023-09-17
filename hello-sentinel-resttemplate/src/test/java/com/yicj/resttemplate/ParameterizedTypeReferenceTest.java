package com.yicj.resttemplate;

import com.yicj.common.model.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


/**
 * @author: yicj
 * @date: 2023/9/17 13:33
 */
@Slf4j
public class ParameterizedTypeReferenceTest {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void hello(){
        String url = "http://localhost:8080/user-service/hello/index" ;
        ParameterizedTypeReference<RestResponse<String>> typeReference =
                new ParameterizedTypeReference<>() {} ;
        ResponseEntity<RestResponse<String>> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), typeReference);
        RestResponse<String> retValue = exchange.getBody();
        log.info("ret value : {}", retValue);
    }
}
