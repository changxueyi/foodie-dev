package com.imooc.enums;

/**
 * @ClassName YesOrNo
 * @Description 是否 枚举
 * @Author changxueyi
 * @Date 2020/3/27 16:46
 */
public enum YesOrNo {
    NO(0,"否"),
    YES(1,"是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}