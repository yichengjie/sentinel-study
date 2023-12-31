#### 硬编码方式配置限流规则
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
3. 编写启动类
   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class SentinelGatewayApplication {
       public static void main(String[] args) {
           System.setProperty(SentinelConfig.APP_TYPE_PROP_KEY, "1");
           System.setProperty("csp.sentinel.dashboard.server","localhost:8858");
           System.setProperty(SentinelConfig.PROJECT_NAME_PROP_KEY,"hello-sentinel-gateway");
           // 以上参数需要配置
           SpringApplication.run(SentinelGatewayApplication.class, args) ;
       }
   }
   ```
4. 编写限流规则
   ```java
   @Configuration
   public class FlowRuleConfig {
       @PostConstruct
       public void init(){
           // 加载限流分组规则
           this.initCustomizeRule();
           // 加载限流分组Api// 这个加载会报错
           this.initCustomizedApis();
       }
       /**
        * 加载限流规则
        */
       private void initCustomizeRule(){
           log.info("------加载限流分组规则----------");
           Set<GatewayFlowRule> list = new HashSet<>() ;
           //1. 按照微服务routeId限流（针对整个微服务）
           GatewayFlowRule rule = new GatewayFlowRule("hello_nacos_client") ;
           rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID) ;
           rule.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
           rule.setIntervalSec(60) ;
           rule.setCount(4) ;
           list.add(rule) ;
           //2. 按照api分组限流(针对api)
           GatewayFlowRule rule1 = new GatewayFlowRule("hello_nacos_client_1") ;
           // setResourceMode、setGrade 可以不设置
           rule1.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME) ;
           rule1.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
           rule1.setIntervalSec(2) ;
           rule1.setCount(1) ;
           list.add(rule1) ;
           //
           GatewayFlowRule rule2 = new GatewayFlowRule("hello_nacos_client_2") ;
           // setResourceMode、setGrade 可以不设置
           rule2.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME) ;
           rule2.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
           rule2.setIntervalSec(10) ;
           rule2.setCount(2) ;
           list.add(rule2) ;
           //
           GatewayRuleManager.loadRules(list) ;
       }
       private void initCustomizedApis() {
           Set<ApiDefinition> definitions = new HashSet<>();
           ApiDefinition api1 = new ApiDefinition("hello_nacos_client_1")
                   .setPredicateItems(new HashSet<>() {{
                       add(new ApiPathPredicateItem().setPattern("/hello-nacos/hello/index"));
                   }});
           ApiDefinition api2 = new ApiDefinition("hello_nacos_client_2")
                   .setPredicateItems(new HashSet<>() {{
                       add(new ApiPathPredicateItem().setPattern("/hello-nacos/user/add"));
                       add(new ApiPathPredicateItem().setPattern("/hello-nacos/user/**")
                               .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                   }});
           definitions.add(api1);
           definitions.add(api2);
           GatewayApiDefinitionManager.loadApiDefinitions(definitions);
       }
   }
   ```
5. 配置网关路由规则
    ```properties
    spring.cloud.gateway.enabled=true
    spring.cloud.gateway.discovery.locator.lower-case-service-id=true
    spring.cloud.gateway.routes[0].id=hello-nacos-client
    spring.cloud.gateway.routes[0].uri=lb://hello-nacos-client
    spring.cloud.gateway.routes[0].predicates[0]=Path=/hello-nacos/**
    spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1
    ```
6. 浏览器访问：http://localhost:8080/hello-nacos/hello/index
#### JSON文件配置限流规则
1. 在【硬编码方式配置限流规则】的基础上去除FlowRuleConfig配置
2. application.properties中添加配置
   ```properties
   # 配置限流规则    
   spring.cloud.sentinel.datasource.ds1.file.file=classpath:sentinel/gateway-flow-rule-sentinel.json
   spring.cloud.sentinel.datasource.ds1.file.dataType=json  
   spring.cloud.sentinel.datasource.ds1.file.rule-type=gw-flow
   # 配置API分组
   spring.cloud.sentinel.datasource.ds2.file.file=classpath:sentinel/gateway-flow-api-group-sentinel.json
   spring.cloud.sentinel.datasource.ds2.file.dataType=json  
   spring.cloud.sentinel.datasource.ds2.file.rule-type=gw-api-group
   ```
3. 在resources目录下新建目录sentinel
4. 在resource/sentinel目录中新建gateway-flow-rule-sentinel.json
   ```json
   [
     {
       "resource": "hello_nacos_client_1",
       "grade": 1,
       "resourceMode": 1,
       "intervalSec": 2,
       "count": 1
     },
     {
        "resource": "hello_nacos_client_2",
        "grade": 1,
        "resourceMode": 1,
        "intervalSec": 10,
        "count": 2
      }
   ]
   ```
5. 在resource/sentinel目录中新建gateway-flow-api-group-sentinel.json
   ```json
   [
     {
       "apiName": "hello_nacos_client_1",
       "predicateItems":[
           {
               "pattern": "/hello-nacos/hello/index"
           }
       ]
     },
     {
         "apiName": "hello_nacos_client_2",
         "predicateItems":[
             {
                 "pattern": "/hello-nacos/user/add"
             },
             {
                "pattern": "/hello-nacos/user/*",
                "matchStrategy": 1
             }
         ]
       }
   ]
   ```
#### Nacos文件配置限流规则
1. 在【硬编码方式配置限流规则】的基础上去除FlowRuleConfig配置
2. pom添加依赖
   ```xml
   <dependency>
      <groupId>com.alibaba.csp</groupId>
      <artifactId>sentinel-datasource-nacos</artifactId>
   </dependency>
   ```
3. application.properties中添加配置
   ```properties
   # 配置限流规则    
   spring.cloud.sentinel.datasource.ds1.nacos.dataId=gateway-flow-rule-sentinel.json
   spring.cloud.sentinel.datasource.ds1.nacos.groupId=DEFAULT_GROUP
   spring.cloud.sentinel.datasource.ds1.nacos.dataType=json
   spring.cloud.sentinel.datasource.ds1.nacos.rule-type=gw-flow
   # 配置API分组
   spring.cloud.sentinel.datasource.ds2.nacos.dataId=gateway-flow-api-group-sentinel.json
   spring.cloud.sentinel.datasource.ds2.nacos.groupId=DEFAULT_GROUP
   spring.cloud.sentinel.datasource.ds2.nacos.dataType=json
   spring.cloud.sentinel.datasource.ds2.nacos.rule-type=gw-api-group
   ```
4. 在nacos控制台上新建gateway-flow-rule-sentinel.json与gateway-flow-api-group-sentinel.json文件并填充JSON内容
#### Dashboard 启动
1. 执行启动命令
   ```text
   java -Dcsp.sentinel.app.type=1 -Dcsp.sentinel.dashboard.server=localhost:8858 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.6.jar
   ```
#### 启动dashboard上查看不到网关服务 (使用spring-cloud-alibaba-sentinel-gateway启动则csp.sentinel.app.type=1非必须)
1. 方式一：添加vm启动参数 
   ```
   -Dcsp.sentinel.app.type=1 
   -Dcsp.sentinel.dashboard.server=localhost:8858
   -Dproject.name=hello-sentinel-gateway
   ```
2. 方式二：启动类中添加参数
   ```text
   System.setProperty(SentinelConfig.APP_TYPE_PROP_KEY, "1");
   System.setProperty("csp.sentinel.dashboard.server","localhost:8858");
   System.setProperty(SentinelConfig.PROJECT_NAME_PROP_KEY,"hello-sentinel-gateway");
   ```