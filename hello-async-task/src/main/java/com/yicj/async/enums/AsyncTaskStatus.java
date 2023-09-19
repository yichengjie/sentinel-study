package com.yicj.async.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yicj
 * @date 2023年09月19日 13:57
 */
@Getter
@AllArgsConstructor
public enum AsyncTaskStatus {

    STARTED(0, "初始化"),
    RUNNING(1, "运行中"),
    ENDED(2, "已结束") ;

    private Integer code ;

    private String message;
}
