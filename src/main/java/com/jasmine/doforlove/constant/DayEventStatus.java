package com.jasmine.doforlove.constant;

import com.jasmine.doforlove.base.BaseEnum;
import lombok.Getter;

@Getter
public enum DayEventStatus implements BaseEnum<Integer> {
    ENABLE(1, "可用"),
    DISABLE(0, "禁用"),
    DONE(2, "已办");
    private Integer code;
    private String msg;
    DayEventStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
