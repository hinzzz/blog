package com.hinz.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.blog.mapper.CategoryMapper;
import com.hinz.blog.service.CategoryService;
import com.hinz.blog.model.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类服务实现类
 * @author hinz
 * @since 2020-07-13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAllCategory() {
        return categoryMapper.findAllCategory();
    }

}
