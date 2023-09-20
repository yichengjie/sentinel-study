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
//@Configuration
public class FlowRuleConfig {
    @PostConstruct
    public void init(){
        // 加载限流分组规则
        this.initCustomizeRule();
        // 加载限流分组Api
        this.initCustomizedApis();
    }




    /**
     * 加载限流规则
     */
    private void initCustomizeRule(){
        log.info("------加载限流分组规则----------");
        Set<GatewayFlowRule> list = new HashSet<>() ;
        //1. 按照微服务routeId限流（针对整个微服务）
        GatewayFlowRule rule = new GatewayFlowRule("hello-nacos-client") ;
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID) ;
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
        rule.setIntervalSec(60) ;
        rule.setCount(4) ;
        list.add(rule) ;

        //2. 按照api分组限流(针对api)
        GatewayFlowRule rule1 = new GatewayFlowRule("hello-nacos-client-1") ;
        rule1.setIntervalSec(10) ;
        rule1.setCount(1) ;
        list.add(rule1) ;
        //
        GatewayFlowRule rule2 = new GatewayFlowRule("hello-nacos-client-2") ;
        rule2.setIntervalSec(10) ;
        rule2.setCount(2) ;
        list.add(rule2) ;
        //
        GatewayRuleManager.loadRules(list) ;
    }

    /**
     * 加载限流分组api
     */
    private void initCustomizedApis() {
        log.info("------加载限流分组Api----------");
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("hello-nacos-client-1")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/hello/index"));
                }});
        ApiDefinition api2 = new ApiDefinition("hello-nacos-client-2")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/user/**")
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                }});
        definitions.add(api1);
        definitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }


}
