1. 默认情况下url触发限流后会直接返回： Blocked by Sentinel (flow limiting)
2. 编写BlockExceptionHandler的实现类
    ```java
    @Component
    public class CustomBlockExceptionHandler implements BlockExceptionHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            String message = "{\"code\": \"999\", \"message\": \"访问人数过多\"}" ;
            response.getWriter().write(message);
        }
    }
    ```
3. 编写业务代码
    ```java
    @RestController
    @RequestMapping("/test")
    public class TestController {
        @GetMapping("/block")
        public String block(){
            return "Hello Sentinel" ;
        }
    }
    ```
4. 在sentinel-dashboard上配置/test/block的流控规则(QPS的阈值设置为1)
5. 再次快速刷新```http://localhost:8083/test/block```，可以看到自定义的流控处理输出