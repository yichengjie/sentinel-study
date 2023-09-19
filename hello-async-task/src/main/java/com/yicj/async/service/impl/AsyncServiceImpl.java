package com.yicj.async.service.impl;

import com.yicj.async.service.IAsyncService;
import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author yicj
 * @date 2023年09月19日 11:21
 */
@Slf4j
@Service
public class AsyncServiceImpl implements IAsyncService {

    @Override
    @Async
    public ListenableFuture<String> asyncImport(List<String> list, String taskId) {
        log.info("异步导入工作进行中, 请耐性等待...");
        if (list.size() > 10){
            throw new RuntimeException("任务条数太大，请传入合适的任务条数!") ;
        }
        CommonUtil.sleepQuiet(3000);
        String uuid = CommonUtil.uuid();
        AsyncResult<String> result = new AsyncResult<>(uuid) ;
        return result ;
    }
}
