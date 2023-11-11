#### 基本使用
1. 引入依赖
    ```xml
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    ```
2. yml添加配置
    ```yaml
    feign:
      sentinel:
        enabled: true
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