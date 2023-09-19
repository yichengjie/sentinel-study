package com.yicj.async.service;

import com.yicj.async.AsyncTaskApplication;
import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author yicj
 * @date 2023年09月19日 14:43
 */
@Slf4j
@SpringBootTest(classes = AsyncTaskApplication.class)
public class AsyncTaskManagerTest {

    @Autowired
    private AsyncTaskManager taskManager ;


    @Test
    public void asyncImportVoid(){
        List<String> list = Flux.range(1, 5)
                .map(String::valueOf)
                .collectList()
                .block();
        String taskId = taskManager.asyncImportVoid(list);
        // 休息一段时间打印所有的任务状态
        // 注意这里sleep的时间要大于asyncImportVoid任务执行的真实时间，否则获取任务的状态可能不正确
        CommonUtil.sleepQuiet(2200);
        taskManager.listAllAsyncTaskInfo()
                .forEach(item -> log.info("task info : {}", item));

    }

}
