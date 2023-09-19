package com.yicj.async.config;

/**
 * @author yicj
 * @date 2023年09月19日 11:16
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.lang.reflect.Method;
import java.util.Arrays;
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

    @Bean
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

    /**
     * 1. 当方法返回Future<T>，手动调用feature.get则不会回调handleUncaughtException方法
     * 2. 当方法返回Future<T>时，如果不调用手动调用feature.get则异常无法正常抛出
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncUncaughtExceptionHandler();
    }

    /**
     * 自定义异步异常处理器
     */
    static class CustomAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            //此demo只打印日志，实际项目以具体业务需求来处理
            log.error(">>> CustomAsyncUncaughtExceptionHandler,class:{}, method: {}, params: {}, error: {}",
                    method.getDeclaringClass().getSimpleName(), method.getName(), Arrays.toString(params),
                    ex.getMessage());
        }
    }
}
