package com.hinz.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinz.blog.common.util.R;
import com.hinz.blog.model.Article;
import com.hinz.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    protected ArticleService articleService;

    @GetMapping()
    public R list(@RequestParam(value = "current") Integer current, @RequestParam(value = "size") Integer size){
        IPage<Article> page=articleService.findPageByKeyword(new Page<>(current, size),null);
        return R.ok().put("page",page);
    }
}
