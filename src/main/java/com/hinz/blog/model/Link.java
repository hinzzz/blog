package com.hinz.blog.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 链接表
 * @author hinz
 * @since 2020-07-13
 */
@Data
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 链接名称
     */
    private String name;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 链接类型 1：友情链接 2：个人链接
     */
    private Integer type;

}
