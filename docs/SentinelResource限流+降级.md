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
       public String exception() {
           int i = 1/0 ;
           return "Hello Sentinel";
       }
       public String fallbackHello(Throwable e){
           log.error("被降级异常打印：", e);
           return "被降级了" ;
       }
   }
   ```
2. 访问```http://localhost:8083/test/exception```
#### 其他补充
1. blockHandlerHello中的异常参数类型为BlockException，写成Exception则不生效
2. fallbackHello中的异常参数必须是Throwable, 写成别的不生效
3. @SentinelResource注解被 SentinelResourceAspect 切面拦截