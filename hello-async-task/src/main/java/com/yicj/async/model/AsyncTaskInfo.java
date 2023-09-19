package com.yicj.async.model;

import com.yicj.async.enums.AsyncTaskStatus;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yicj
 * @date 2023年09月19日 13:56
 */
@Data
public class AsyncTaskInfo implements Serializable {

    /**
     * 任务id
     */
    private String taskId ;

    /**
     * 开始时间
     */
    private LocalDateTime startTime ;

    /**
     * 结束时间
     */
    private LocalDateTime endTime ;

    /**
     * 任务状态
     */
    private AsyncTaskStatus status ;

}
