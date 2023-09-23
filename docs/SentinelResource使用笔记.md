1. #### SentinelResource限流
2. 编写业务代码
    ```java
    @RestController
    @RequestMapping("/test")
    public class TestController {
        @GetMapping(value = "/hello")
        @SentinelResource(value = "hello", blockHandler = "blockHandlerHello")
        public String hello() {
            return "Hello Sentinel";
        }
        public String blockHandlerHello(BlockException e){
            log.error("被限流了异常打印：", e);
            return "被限流了" ;
        }
    }
    ```
3. 编写流控规则,并按照InitFunc的SPI配置
   ```java
   public class FlowRuleInitFunc implements InitFunc {
       @Override
       public void init() throws Exception {
           List<FlowRule> rules = new ArrayList<>() ;
           var rule = new FlowRule() ;
           rule.setResource("hello") ;
           rule.setGrade(RuleConstant.FLOW_GRADE_QPS) ;
           rule.setCount(1) ;
           rule.setStrategy(RuleConstant.STRATEGY_DIRECT) ;
           rule.setLimitApp("default") ;
           rules.add(rule) ;
           FlowRuleManager.loadRules(rules);
       }
   }
   ```
4. 访问```http://localhost:8083/test/hello```
#### SentinelResource 降级
1. 在TestController中补充业务代码
   ```java
   @RestController
   @RequestMapping("/test")
   public class TestController {
   
       @GetMapping(value = "/exception")
       @SentinelResource(value = "test_exception", fallback = "fallbackHello")
       public String exception(@RequestParam(value = "param", required = false) String param) {
           if (StringUtils.hasText(param)){
              int i = 1/0 ;
           }
           return "Hello Sentinel";
       }
       public String fallbackHello(Throwable e){
           log.error("被降级异常打印：", e);
           return "被降级了" ;
       }
   }
   ```
2. 访问```http://localhost:8083/test/exception?param=1```
#### 熔断配置
1. 在FlowRuleInitFunc中补充熔断配置
   ```java
   @Slf4j
   public class RuleLoadInitFunc implements InitFunc {
       @Override
       public void init() throws Exception {
           log.info("======> 手动加载限流规则.....");
           this.initDegradeRule();
       }
       /**
        * 30秒内，最少3个请求，如果有10% 的报错则触发10秒的熔断降级
        */
       private void initDegradeRule(){
           List<DegradeRule> rules = new ArrayList<>() ;
           DegradeRule rule = new DegradeRule("test_exception")
               .setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())
               .setCount(0.1) // Threshold is 70% error ratio
               .setMinRequestAmount(3) // 熔断触发的最小请求数
               .setStatIntervalMs(30000) // 30s 统计时长
               .setTimeWindow(10); // 熔断时长10秒
           rules.add(rule) ;
           DegradeRuleManager.loadRules(rules);
       }
   }
   ```
2. 访问3次```http://localhost:8083/test/exception?param=1``` 
3. 再次访问```http://localhost:8083/test/exception```发现服务已经处于熔断状态，10秒之后服务恢复正常
#### 其他补充
1. blockHandlerHello中的异常参数类型为BlockException，写成Exception则不生效
2. fallbackHello中的异常参数必须是Throwable, 写成别的不生效
3. @SentinelResource注解被 SentinelResourceAspect 切面拦截