package com.yicj.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

/**
 * @author yicj
 * @date 2023年09月18日 15:33
 */
@Slf4j
public class MonoTest {

    @Test
    public void filterWhen(){
        List<Integer> list = Flux.just( 1, 2, 3, 4, 5)
                .filterWhen(item -> Mono.just(item > 3))
                .collectList().block();
        log.info("list : {}", list);
    }
}
