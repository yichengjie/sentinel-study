package com.yicj.async.service;

import org.springframework.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author yicj
 * @date 2023年09月19日 11:18
 */
public interface IAsyncService {

    Future<String> asyncImport(List<String> list, String taskId) ;

    void asyncImportVoid(List<String> list, String taskId) ;
}
