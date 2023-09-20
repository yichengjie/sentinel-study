1. pom引入依赖
    ```text
    <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
    </dependency>
    ```
2. 添加配置
    ```properties
    spring.cloud.sentinel.transport.dashboard=localhost:8858
    spring.cloud.sentinel.transport.port=8749
    spring.cloud.sentinel.filter.enabled=false
    ```
3. 硬编码限流规则
    ```java
    @Configuration
    public class SentinelConfig {
        @PostConstruct
        public void init(){
            this.initCustomizeRule();
        }
    
        private void initCustomizeRule(){
            Set<GatewayFlowRule> list = new HashSet<>() ;
            GatewayFlowRule rule = new GatewayFlowRule("hello-nacos-client") ;
            rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID) ;
            // qps: 1
            rule.setGrade(1) ;
            rule.setCount(1) ;
            rule.setIntervalSec(1) ;
            rule.setControlBehavior(1) ;
            list.add(rule) ;
            GatewayRuleManager.loadRules(list) ;
        }
    }
    ```
4. 配置网关路由规则
    ```properties
    spring.cloud.gateway.enabled=true
    spring.cloud.gateway.discovery.locator.lower-case-service-id=true
    spring.cloud.gateway.routes[0].id=hello-nacos-client
    spring.cloud.gateway.routes[0].uri=lb://hello-nacos-client
    spring.cloud.gateway.routes[0].predicates[0]=Path=/hello-nacos/**
    spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1
    ```
5. 浏览器访问：http://localhost:8080/hello-nacos/hello/index