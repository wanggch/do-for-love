package com.jasmine.doforlove.constant;

import com.jasmine.doforlove.base.BaseEnum;
import lombok.Getter;

import java.util.Objects;

@Getter
public enum TaskType implements BaseEnum<String> {

    // 每天要做的事情
    DAILY("daily", "0 0 0 * * ?"),
    // 每周周X要做的事情
    WEEKLY("weekly", "0 0 0 ? * {}"),
    // 每月X号要做的事情
    MONTHLY("monthly", "0 0 0 {} * ?"),
    // 每隔X天要做的事情
    INTERVAL("interval", "0 0 0 1/{} * ? "),
    // 只做一次的事情
    ONCE("once", "");

    /**
     * 类型编码
     */
    private String code;
    /**
     * 表达式模板
     */
    private String cronPattern;

    TaskType(String code, String cronPattern) {
        this.code = code;
        this.cronPattern = cronPattern;
    }

    /**
     * 根据类型编码获取任务类型
     * @param code 类型编码
     * @return 任务类型
     */
    public static TaskType getTaskType(String code) {
        for (TaskType taskType : TaskType.values()) {
            if (Objects.equals(taskType.getCode(), code)) {
                return taskType;
            }
        }
        return null;
    }
}
