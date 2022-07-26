package com.jasmine.doforlove.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jasmine.doforlove.base.BaseEntity;
import lombok.Data;

@Data
@TableName("t_task")
public class Task extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String cron;
    private Integer status;
    private String remark;
}
