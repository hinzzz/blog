package com.hinz.blog.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2020/9/16 16:50
 */
@Data
public class Catalogue {
    /**
     * 等级
     */
    private Integer leve;
    /**
     * 标题内容
     */
    private String title;
    /**
     * 源标题内容
     */
    private String sourceTitle;

    /**
     * 子标题
     */
    private List<Catalogue> childs;
}
