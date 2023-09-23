package com.yicj.sentinel.sentinel;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 手动配置流控规则，可以借助Sentinel的InitFunc SPI扩展机制来实现
 * @author: yicj
 * @date: 2023/9/23 8:19
 */
@Slf4j
public class RuleLoadInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {
        log.info("======> 手动加载限流规则.....");
        initFlowRule();
        //
        //this.initDegradeRule();
    }

    /**
     * 每秒一个QPS限流
     */
    private void initFlowRule(){
        List<FlowRule> rules = new ArrayList<>() ;
        var rule = new FlowRule() ;
        rule.setResource("test_hello") ;
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
        rule.setCount(1) ;
        rule.setStrategy(RuleConstant.STRATEGY_DIRECT) ;
        rule.setLimitApp("default") ;
        rules.add(rule) ;
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 30秒内，最少3个请求，如果有10% 的报错则触发10秒的熔断降级
     */
    private void initDegradeRule(){
        List<DegradeRule> rules = new ArrayList<>() ;
        DegradeRule rule = new DegradeRule("test_exception")
            .setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())
            .setCount(0.1) // Threshold is 70% error ratio
            .setMinRequestAmount(3) // 熔断触发的最小请求数
            .setStatIntervalMs(30000) // 30s 统计时长
            .setTimeWindow(10); // 熔断时长10秒
        rules.add(rule) ;
        DegradeRuleManager.loadRules(rules);
    }
}
