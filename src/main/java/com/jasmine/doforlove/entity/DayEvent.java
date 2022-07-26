package com.jasmine.doforlove.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jasmine.doforlove.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_day_event")
public class DayEvent extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String taskId;
    private String name;
    private Integer status;
    private String remark;
    private Date eventDate;
}
