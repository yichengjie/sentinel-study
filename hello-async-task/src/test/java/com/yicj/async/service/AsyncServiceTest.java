package com.yicj.async.service;

import com.yicj.async.AsyncTaskApplication;
import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author yicj
 * @date 2023年09月19日 11:22
 */
@Slf4j
@SpringBootTest(classes = AsyncTaskApplication.class)
public class AsyncServiceTest {

    @Autowired
    private IAsyncService asyncService ;

    @Test
    public void asyncImport() throws Exception {
        String taskId = CommonUtil.uuid();
        AtomicInteger count = new AtomicInteger() ;
        List<String> list = Stream.generate(count::getAndIncrement)
                .limit(5)
                .map(String::valueOf)
                .toList();
        Future<String> result = asyncService.asyncImport(list, taskId);
        log.info("ret value : {}", result.get());
    }


    @Test
    public void asyncImportException() throws Exception {
        String taskId = CommonUtil.uuid();
        AtomicInteger count = new AtomicInteger() ;
        List<String> list = Stream.generate(count::getAndIncrement)
                .limit(11)
                .map(String::valueOf)
                .toList();
        Future<String> result = asyncService.asyncImport(list, taskId);
        log.info("ret value : {}", result.get());
    }
}
