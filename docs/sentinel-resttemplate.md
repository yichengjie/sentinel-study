1. resttemplate开启sentinel保护配置
    ```properties
    resttemplate.sentinel.enabled=true
    ```
2. 配置sentinel-dashboard地址
   ```properties
   spring.cloud.sentinel.transport.dashboard=localhost:8858\
   spring.cloud.sentinel.transport.dashboard.port=8739 
   ```
3. 实例化RestTemplate并加入@SentinelRestTemplate注解
   ```java
   @Configuration
   public class RestTemplateConfig {
       @Bean
       @LoadBalanced
       @SentinelRestTemplate(
               fallbackClass = ExceptionUtil.class,fallback = "fallBack",
               blockHandlerClass = ExceptionUtil.class, blockHandler = "handleBlock")
       public RestTemplate restTemplate() {
           return new RestTemplate();
       }
   
       public static class ExceptionUtil {
           public static ClientHttpResponse handleBlock(
                   HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException be){
               RestResponse<Void> commonResult =  RestResponse.error("500","降级处理函数 block 。。。。。");
               return new SentinelClientHttpResponse(JSON.toJSONString(commonResult));
           }
   
           public static ClientHttpResponse fallBack(
                   HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException be){
               RestResponse<Void> commonResult = RestResponse.error("500","异常处理函数 fallback 。。。。。");
               return new SentinelClientHttpResponse(JSON.toJSONString(commonResult));
           }
       }
   }
   ```
4. 编写测试代码,并使用postman访问对应的url
   ```java
   @Slf4j
   @RestController
   @RequestMapping("/test")
   public class TestController {
       @Autowired
       private RestTemplate restTemplate ;
   
       @GetMapping("/index")
       public RestResponse<String> index(){
           String url = "http://hello-nacos-client/hello/index" ;
           RestResponse<String> retValue = restTemplate.getForObject(url, RestResponse.class);
           log.info("ret value : {}", retValue);
           return retValue ;
       }
        
       @GetMapping("/exception")
       public Object exception(){
           String url = "http://hello-nacos-client/hello/exception" ;
           RestResponse<String> retValue = restTemplate.getForObject(url, RestResponse.class);
           log.info("ret value : {}", retValue);
           return retValue ;
       }
   }
   ```
5. 在dashboard上配置限流规则，再次通过postman调用url，能正常触发SentinelRestTemplate的blockHandler方法处理
6. 在dashboard上配置熔断规则，再次通过postman调用url，能正常触发SentinelRestTemplate的fallBack方法处理