1. 启动脚本：
    ```text
    java -Dserver.port=8858 -Dcsp.sentinel.dashboard.server=localhost:8858 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.6.jar
    ```
2. 网关启动 -Dcsp.sentinel.app.type=1
    ```text
    java -Dcsp.sentinel.app.type=1 -Dcsp.sentinel.dashboard.server=localhost:8858 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.6.jar
    ```