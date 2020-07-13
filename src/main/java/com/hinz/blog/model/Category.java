package com.hinz.blog.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 分类表
 * @author hinz
 * @since 2020-07-13
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    private String name;

    /**
     * 分类链接
     */
    @NotBlank(message = "分类链接不能为空")
    private String url;
}
