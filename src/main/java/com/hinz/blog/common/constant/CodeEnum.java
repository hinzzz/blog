package com.hinz.blog.common.constant;

/**
 * 全局状态码
 * @author hinz
 * @date 2020/07/13
 */
public enum CodeEnum {
    /**
     * 成功
     */
    SUCCESS(1),
    /**
     * 失败
     */
    FAIL(0),
    /**
     * 未知错误/其他异常
     */
    UNKNOWN_ERROR(-1),
    /**
     * 未登录
     */
    NOT_LOGIN(40001),

    /**
     * 参数校检不通过
     */
    VALIDATION_ERROR(40002),

    /**
     * 重复插入数据
     */
    DUPLICATE_KEY(40003),

    /**
     * 未找到数据
     */
    NOT_FOUND(40004),

    /**
     * 文章抓取失败
     */
    SPIDER_ERROR(40005),

    /**
     * 文件上传异常
     */
    UPLOAD_ERROR(40006),
    /**
     *点赞异常
     */
    GOODS_ERROR(40007),
    /**
     *收藏异常
     */
    COLLECTS_ERROR(40008);



    private int value;

    CodeEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
