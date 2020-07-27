package com.hinz.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.blog.model.Category;

import java.util.List;

/**
 * 分类服务类
 * @author hinz
 * @since 2020-07-13
 */
public interface CategoryService extends IService<Category> {
    List<Category> findAllCategory();
}
