package com.hinz.blog.model.enums;

/**
 * 链接类型枚举类
 * @author hinz
 * @date 2020-07-13
 */
public enum LinkTypeEnum {
    /**
     * 友情链接
     */
    FRIEND_LINK(1,"友情链接"),
    /**
     * 个人链接
     */
    PERSONAL_LINK(2,"个人链接");


    private int value;
    private String desc;

    LinkTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
