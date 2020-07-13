package com.hinz.blog.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 标签表
 * @author hinz
 * @since 2020-07-13
 */
@Data
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空")
    private String name;

    /**
     * 标签链接
     */
    @NotBlank(message = "标签链接不能为空")
    private String url;
}
