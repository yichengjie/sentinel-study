package com.yicj.sentinel.nacos;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * @author: yicj
 * @date: 2023/9/23 14:18
 */
@Slf4j
public class SentinelNacosTest {

    @Test
    public void hello() throws Exception {
        String remoteAddress = "127.0.0.1:8848" ;
        String groupId = "DEFAULT_GROUP" ;
        String dataId = "gateway-flow-rule-sentinel.json" ;
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(
                remoteAddress, groupId, dataId, new MyConverter());
        // 注册监听，当数据变更的限流规则会重新构建并刷新到FlowRuleManager中
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }


    static class MyConverter implements Converter<String, List<FlowRule>>{
        @Override
        public List<FlowRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<FlowRule>>(){}) ;
        }
    }
}
