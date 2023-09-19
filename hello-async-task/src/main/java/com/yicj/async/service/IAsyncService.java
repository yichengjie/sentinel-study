package com.yicj.async.service;

import org.springframework.util.concurrent.ListenableFuture;
import java.util.List;

/**
 * @author yicj
 * @date 2023年09月19日 11:18
 */
public interface IAsyncService {

    ListenableFuture<String> asyncImport(List<String> list, String taskId) ;
}
