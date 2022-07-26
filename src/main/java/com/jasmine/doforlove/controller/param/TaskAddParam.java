package com.jasmine.doforlove.controller.param;

import lombok.Data;

@Data
public class TaskAddParam {
    // 任务名称
    private String name;
    // 任务类型：每天、每周、每月、每隔N天、只执行一次
    // 每周周几、每月的哪一天、每隔几天、只执行一次是在哪天执行
    private String type;
    // 值、内容
    private String content;
    // 备注、说明
    private String remark;
}
