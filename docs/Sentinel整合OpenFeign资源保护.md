#### 基本使用
1. 引入依赖
    ```xml
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    ```
2. 添加配置
    ```properties
    # 1. sentinel保护feign开关
    feign.sentinel.enabled=true
    # 2. sentinel dashboard 配置
    spring.cloud.sentinel.transport.dashboard=localhost:8858
    spring.cloud.sentinel.transport.port=8729
    # 3. 基础配置
    spring.application.name=hello-sentinel-feign
    spring.cloud.nacos.discovery.server-addr=localhost:8848
    spring.cloud.loadbalancer.nacos.enabled=true
    ```
3. 编写feign接口并配置fallback属性
    ```java
    @FeignClient(value = "nacos-client-app"
            , contextId = "nacosHelloClient"
            , fallback = NacosHelloClientFallback.class
    )
    public interface NacosHelloClient {

        @GetMapping("/hello/index")
        String hello() ;

        @GetMapping("/hello/exception")
        String exception() ;
    }
    ```
4. 编写fallback实现代码
    ```java
    @Component
    public class NacosHelloClientFallback implements NacosHelloClient {

        @Override
        public String hello() {
            return "fallback hello ret value";
        }
        @Override
        public String exception() {
            return "fallback exception ret value";
        }
    }
    ```
#### 配置降级规则
1. 添加配置
    ```properties
    # 配置降级规则
    spring.cloud.sentinel.datasource.ds1.file.dataType=json
    spring.cloud.sentinel.datasource.ds1.file.file=classpath:sentinel/feign-degrade-sentinel.json
    spring.cloud.sentinel.datasource.ds1.file.rule-type=degrade
    ```
2. 编写sentinel/feign-degrade-sentinel.json降级规则 (慢调用降级规则)
   ```json
   [{
       "resource": "GET:http://nacos-client-app/hello/index",
       "grade": 0,
       "count": 200,
       "timeWindow": 11,
       "minRequestAmount": 6,
       "statIntervalMs": 10000,
       "slowRatio":0.3
   }]
   ```