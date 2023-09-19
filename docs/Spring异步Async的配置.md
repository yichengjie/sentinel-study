1. 编写线程池配置类
    ```java
    @Slf4j
    @EnableAsync
    @Configuration
    public class AsyncConfig implements AsyncConfigurer {
        // 注意这里需要使用
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
    ```
2. 编写业务验证方法(方法上使用@Async注解标注)
    ```java
    @Slf4j
    @Service
    public class AsyncServiceImpl {
        @Async("getAsyncExecutor")
        public Future<String> asyncImport(List<String> list, String taskId) {
            log.info("异步导入工作进行中, 请耐性等待...");
            if (list.size() > 10){
                int i = 1 / 0;
            }
            CommonUtil.sleepQuiet(2000);
            String uuid = CommonUtil.uuid();
            return new AsyncResult<>(uuid) ;
        }
        @Async("getAsyncExecutor")
        public void asyncImportVoid(List<String> list, String taskId) {
            log.info("异步导入工作进行中, 请耐性等待...");
            if (list.size() > 10){
                int i = 1 / 0;
            }
            CommonUtil.sleepQuiet(3000);
        }
    }
    ```
3. 