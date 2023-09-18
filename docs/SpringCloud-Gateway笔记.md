1. 入口配置类：GatewayAutoConfiguration
2. 配置RoutePredicateHandlerMapping类
3. FilteringWebHandler(List<GlobalFilter> filters) -> handle(exchange)
4. DefaultGatewayFilterChain