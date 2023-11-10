#### open-feign整合sentinel （待验证）
1. 添加配置
    ```properties
    feign.sentinel.enabled=true
    ```
2. 配置限流规则并保存到nacos中
    ```json
    [{
      "resource": "GET:http://service-provider/echo/{str}",
      "grade": 0,
      "count": 201,
      "timeWindow": 11,
      "minRequestAmount": 6,
      "statIntervalMs": 1000,
      "slowRatio":0.3
    }]
    ```
3. 添加sentinel与nacos的datasource依赖
4. 配置sentinel与nacos的datasource