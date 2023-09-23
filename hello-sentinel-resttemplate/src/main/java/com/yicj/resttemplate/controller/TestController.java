package com.yicj.resttemplate.controller;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.fastjson.TypeReference;
import com.yicj.common.model.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: yicj
 * @date: 2023/9/16 12:16
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate ;

    @GetMapping("/index")
    public RestResponse<String> index(){
        String url = "http://hello-nacos-client/hello/index" ;
        RestResponse<String> retValue = restTemplate.getForObject(url, RestResponse.class);
        log.info("ret value : {}", retValue);
        return retValue ;
    }

    @GetMapping("/exception")
    public Object exception(){
        String url = "http://hello-nacos-client/hello/exception" ;
        //TypeReference<RestResponse<String>> typeReference = new TypeReference<>() ;
        ParameterizedTypeReference<RestResponse<String>> typeReference = new ParameterizedTypeReference<>() {};
        ResponseEntity<RestResponse<String>> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), typeReference);
        RestResponse<String> retValue = exchange.getBody();
        log.info("ret value : {}", retValue);
        return retValue ;
    }

    @PostConstruct
    public void init(){

        //this.initDegradeRule();
    }

    /**
     * 配置服务的熔断规则
     */
    private void initDegradeRule(){
        List<DegradeRule> rules = new ArrayList<>() ;
        DegradeRule rule = new DegradeRule() ;
        rule.setResource("KEY");
        rule.setCount(2) ;
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT) ;
        rule.setTimeWindow(10) ;
        rule.setMinRequestAmount(3) ;
        rules.add(rule) ;
        DegradeRuleManager.loadRules(rules);
    }
}
