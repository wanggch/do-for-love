package com.jasmine.doforlove.util;

import com.jasmine.doforlove.base.BaseEnum;

import java.util.Objects;

/**
 * 枚举工具类
 * @author: wanggc
 */
public class BaseEnumUtil {

    public static <E extends BaseEnum> E codeOf(Class<E> enumClass, String code) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.getCode() instanceof Integer) {
                if (Objects.equals(Integer.parseInt(code), e.getCode())) {
                    return e;
                }
            } else if (Objects.equals(code, e.getCode())) {
                return e;
            }
        }
        return null;
    }
}
