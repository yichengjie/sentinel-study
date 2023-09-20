package com.yicj.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yicj
 * @date 2023年09月20日 9:32
 */
@Slf4j
@Configuration
public class FlowRuleConfig {

    @PostConstruct
    public void init(){
        // 加载限流分组规则
        this.initCustomizeRule();
        // 加载限流分组Api
        //this.initCustomizedApis2();
    }


    /**
     * 加载限流规则
     */
    private void initCustomizeRule(){
        log.info("------加载限流分组规则----------");
        Set<GatewayFlowRule> list = new HashSet<>() ;
        //1. 按照微服务routeId限流（针对整个微服务）
        GatewayFlowRule rule = new GatewayFlowRule("hello_nacos_client") ;
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID) ;
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
        rule.setIntervalSec(60) ;
        rule.setCount(4) ;
        list.add(rule) ;

        //2. 按照api分组限流(针对api)
        GatewayFlowRule rule1 = new GatewayFlowRule("hello_nacos_client_1") ;
        // 这里不能配置，否则dashboard上网关服务不正常
        //rule1.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME) ;
        //rule1.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
        rule1.setIntervalSec(10) ;
        rule1.setCount(1) ;
        list.add(rule1) ;
        //
        GatewayFlowRule rule2 = new GatewayFlowRule("hello_nacos_client_2") ;
        //rule2.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME) ;
        //rule2.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
        rule2.setIntervalSec(10) ;
        rule2.setCount(2) ;
        list.add(rule2) ;
        //
        GatewayRuleManager.loadRules(list) ;
    }



    private void initCustomizedApis2() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("hello_nacos_client_1")
                .setPredicateItems(new HashSet<>() {{
                    add(new ApiPathPredicateItem().setPattern("/hello-nacos/hello/index"));
                    add(new ApiPathPredicateItem().setPattern("/hello-nacos/user/**")
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                }});
        ApiDefinition api2 = new ApiDefinition("hello_nacos_client_2")
                .setPredicateItems(new HashSet<>() {{
                    add(new ApiPathPredicateItem().setPattern("/hello-nacos/user/add"));
                }});
        definitions.add(api1);
        definitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

}
