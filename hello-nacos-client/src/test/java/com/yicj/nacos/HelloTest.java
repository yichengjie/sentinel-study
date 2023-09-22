package com.yicj.nacos;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;


/**
 * @author: yicj
 * @date: 2023/9/22 14:45
 */
@Slf4j
@SpringBootTest(classes = NacosClientApplication.class)
public class HelloTest {

    @Autowired
    private NacosConfigManager configManager ;

    @Test
    public void hello() throws NacosException, IOException {
        ConfigService configService = configManager.getConfigService();
        String dataId = "gateway-flow-api-group-sentinel.json" ;
        String groupId = "DEFAULT_GROUP" ;
        String content = configService.getConfig(dataId, groupId, 3000);
        log.info("content : {}", content);
    }

}
