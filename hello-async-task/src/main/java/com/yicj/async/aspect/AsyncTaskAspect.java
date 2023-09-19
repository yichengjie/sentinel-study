package com.yicj.async.aspect;

import com.yicj.async.enums.AsyncTaskStatus;
import com.yicj.async.model.AsyncTaskInfo;
import com.yicj.async.service.AsyncTaskManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author yicj
 * @date 2023年09月19日 14:15
 */
@Aspect
@Component
public class AsyncTaskAspect {

    @Autowired
    private AsyncTaskManager taskManager ;

    @Around("execution(* com.yicj.async.service.impl.AsyncServiceImpl.*(..))")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) throws Throwable{
        Object[] args = joinPoint.getArgs();
        String taskId = (String) args[1] ;
        AsyncTaskInfo taskInfo = taskManager.findAsyncTaskInfoById(taskId);
        Object result ;
        try {
            taskInfo.setStatus(AsyncTaskStatus.RUNNING);
            result = joinPoint.proceed();
        }finally {
            taskInfo.setEndTime(LocalDateTime.now());
            taskInfo.setStatus(AsyncTaskStatus.ENDED);
            taskManager.updateAsyncTaskInfo(taskInfo);
        }
        return result ;
    }
}
