package com.hinz.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinz.blog.common.constant.CodeEnum;
import com.hinz.blog.common.constant.Const;
import com.hinz.blog.common.exception.BlogException;
import com.hinz.blog.common.util.R;
import com.hinz.blog.service.CategoryService;
import com.hinz.blog.model.Article;
import com.hinz.blog.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台分类控制器
 * @author hinz
 * @date 2020-07-13
 */
@RestController
@RequestMapping("category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public R list(){
        return R.ok().put("category",categoryService.findAllCategory());
    }
}
