package com.jasmine.doforlove.constant;

import com.jasmine.doforlove.base.BaseEnum;
import lombok.Getter;

@Getter
public enum Status implements BaseEnum<Integer> {
    ENABLE(1, "可用"),
    DISABLE(0, "禁用");
    private Integer code;
    private String msg;
    Status(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
