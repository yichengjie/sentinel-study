package com.yicj.gateway;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.nacos.client.config.utils.ContentUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author: yicj
 * @date: 2023/9/17 21:47
 */
@Slf4j
public class FlowRuleTest {

    @Test
    public void hello(){
        FlowRule rule = new FlowRule() ;
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
        rule.setCount(0) ;
        rule.setRefResource("resourceA") ;
        rule.setStrategy(RuleConstant.STRATEGY_CHAIN) ;
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT) ;
        FlowRuleManager.loadRules(Arrays.asList(rule));
        //
        ContextUtil.enter("my-entrance-node1") ;
        Entry entryA = null ;
        try {
            entryA = SphU.entry("resourceA" );
        }catch (BlockException e){
            log.error("my-entrance-node1 block by Sentinel: ", e.getCause());
        }finally {
            if (entryA != null){
                entryA.exit();
            }
            ContextUtil.exit();
        }
        //
        ContextUtil.enter("my-entrance-node2") ;
        try {
            entryA = SphU.entry("resourceA") ;
        }catch (BlockException e){
            log.error("my-entrance-node2 block my Sentinel:", e.getCause());
        }finally {
            if (entryA != null){
                entryA.exit();
            }
            ContextUtil.exit();
        }
    }
}
