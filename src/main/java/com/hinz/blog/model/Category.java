package com.hinz.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

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
    /**
     * 分类描述
     */
    private String description;

    /**
     * 当前分类文章总数
     */
    @TableField(exist = false)
    private Integer total;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;
}
