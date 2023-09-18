package com.yicj.webmvc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author yicj
 * @date 2023年09月18日 18:05
 */
@Data
@ConfigurationProperties(prefix = "custom")
public class CustomProperties implements Serializable {

    private List<UserProperties> users = Collections.emptyList() ;
}
