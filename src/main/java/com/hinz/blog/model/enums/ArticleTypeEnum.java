package com.hinz.blog.model.enums;

/**
 * 文章类型枚举类
 * @author hinz
 * @date 2020-07-13
 */
public enum ArticleTypeEnum {

    /**
     * 普通文章
     */
    ORDINARY(0, "普通文章"),

    /**
     * 自定义
     */
    CUSTOM(1, "自定义");

    private Integer value;
    private String desc;

    ArticleTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
