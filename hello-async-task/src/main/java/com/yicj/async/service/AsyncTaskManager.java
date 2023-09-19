package com.yicj.async.service;

import com.yicj.async.enums.AsyncTaskStatus;
import com.yicj.async.model.AsyncTaskInfo;
import com.yicj.common.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yicj
 * @date 2023年09月19日 13:56
 */
@Component
public class AsyncTaskManager {

    private Map<String,AsyncTaskInfo> taskInfoMap = new ConcurrentHashMap<>();

    @Autowired
    private IAsyncService asyncService ;


    public String asyncImportVoid(List<String> list){
        AsyncTaskInfo taskInfo = initTaskInfo();
        String taskId = taskInfo.getTaskId();
        this.asyncService.asyncImport(list, taskId) ;
        return taskId ;
    }


    public AsyncTaskInfo findAsyncTaskInfoById(String taskId){

        return taskInfoMap.get(taskId) ;
    }

    public List<AsyncTaskInfo> listAllAsyncTaskInfo(){
        return taskInfoMap.values().stream().toList() ;
    }

    public void updateAsyncTaskInfo(AsyncTaskInfo taskInfo){
        taskInfoMap.put(taskInfo.getTaskId(), taskInfo) ;
    }


    private AsyncTaskInfo initTaskInfo(){
        AsyncTaskInfo taskInfo = new AsyncTaskInfo() ;
        taskInfo.setTaskId(CommonUtil.uuid());
        taskInfo.setStartTime(LocalDateTime.now());
        taskInfo.setStatus(AsyncTaskStatus.STARTED);
        taskInfoMap.put(taskInfo.getTaskId(), taskInfo) ;
        return taskInfo ;
    }


}
