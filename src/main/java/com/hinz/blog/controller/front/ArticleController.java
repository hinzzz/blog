package com.hinz.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinz.blog.common.constant.Const;
import com.hinz.blog.model.Article;
import com.hinz.blog.service.ArticleService;
import com.hinz.blog.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：quanhz
 * @date ：Created in 2020/7/15 15:44
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    protected ArticleService articleService;

    @GetMapping("/list/{pageIndex}")
    public R list(@PathVariable(value = "pageIndex") Integer pageIndex){
        IPage<Article> page=articleService.findPageByKeyword(new Page<>(pageIndex, Const.PAGE_SIZE),null);
        return R.ok().put("page", page);
    }

}
