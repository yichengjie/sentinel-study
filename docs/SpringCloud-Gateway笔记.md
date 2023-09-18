1. 入口配置类：GatewayAutoConfiguration
2. 配置RoutePredicateHandlerMapping类
3. FilteringWebHandler(List<GlobalFilter> filters) -> handle(exchange)
4. DefaultGatewayFilterChain
### RouteDefinitionLocator 初始化
1. PropertiesRouteDefinitionLocator
2. InMemoryRouteDefinitionRepository
### RouteDefinitionRouteLocator初始化

