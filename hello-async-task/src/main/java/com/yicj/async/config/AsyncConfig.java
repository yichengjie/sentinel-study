package com.yicj.async.config;

/**
 * @author yicj
 * @date 2023年09月19日 11:16
 */

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yicj
 * @date 2023年09月19日 10:42
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(50);
        threadPool.setMaxPoolSize(100);
        threadPool.setKeepAliveSeconds(10);
        threadPool.setQueueCapacity(1000);
        threadPool.setThreadNamePrefix("custom-task-pool-");
        //
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.setAwaitTerminationSeconds(5);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("async task execute fail : " , ex);
            log.info("method name : {}, params: {}", method.getName(), JSON.toJSONString(params));
            // 发送邮件通知或则计入数据库
        };
    }
}
