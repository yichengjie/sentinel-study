package com.yicj.sentinel.config;

import com.alibaba.cloud.circuitbreaker.sentinel.SentinelCircuitBreakerAutoConfiguration;
import com.alibaba.cloud.circuitbreaker.sentinel.SentinelCircuitBreakerFactory;
import com.alibaba.cloud.circuitbreaker.sentinel.SentinelConfigBuilder;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author: yicj
 * @date: 2023/9/17 21:20
 */
@Configuration
public class AppConfig {

    /**
     * @return Customizer
     * @see SentinelCircuitBreakerAutoConfiguration
     */
    @Bean
    public Customizer<SentinelCircuitBreakerFactory> customizer() {
        return factory -> {
            factory.configureDefault(id -> new SentinelConfigBuilder().resourceName(id)
                    .rules(Collections.singletonList(new DegradeRule(id)
                            .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT)
                            .setCount(3).setTimeWindow(10)))
                    .build()
            );
            //
            factory.configure(builder ->
                    builder.rules(Collections.singletonList(new DegradeRule("slow")
                            .setGrade(RuleConstant.DEGRADE_GRADE_RT).setCount(100)
                            .setTimeWindow(5)
                    )), "rt");

        };
    }

}
