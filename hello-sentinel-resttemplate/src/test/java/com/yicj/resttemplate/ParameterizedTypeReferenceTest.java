package com.yicj.resttemplate;

import com.yicj.common.model.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import java.lang.reflect.Type;

/**
 * @author: yicj
 * @date: 2023/9/17 13:33
 */
@Slf4j
public class ParameterizedTypeReferenceTest {

    @Test
    public void hello(){
        ParameterizedTypeReference<RestResponse<String>> typeReference =
                new ParameterizedTypeReference<RestResponse<String>>() {} ;
        Type type = typeReference.getType();
        log.info("type : {}", type);
    }

}
