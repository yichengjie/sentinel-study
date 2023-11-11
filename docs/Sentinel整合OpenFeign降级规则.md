1. 添加配置
    ```properties
    # 打开sentinel 对feign保护开关
    feign.sentinel.enabled=true
    # 配置降级规则
    spring.cloud.sentinel.datasource.ds1.file.dataType=json
    spring.cloud.sentinel.datasource.ds1.file.file=classpath:sentinel/feign-degrade-sentinel.json
    spring.cloud.sentinel.datasource.ds1.file.rule-type=degrade
    ```
2. 编写降级规则
   ```json
   [{
       "resource": "GET:http://hello-service-provider/echo/fixed/hello",
       "grade": 0,
       "count": 201,
       "timeWindow": 11,
       "minRequestAmount": 6,
       "statIntervalMs": 10000,
       "slowRatio":0.3
   }]
   ```