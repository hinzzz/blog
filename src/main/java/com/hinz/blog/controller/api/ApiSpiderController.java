package com.hinz.blog.controller.api;


import com.hinz.blog.common.constant.CodeEnum;
import com.hinz.blog.common.exception.BlogException;
import com.hinz.blog.common.util.Result;
import com.hinz.blog.common.util.ValidatorUtils;
import com.hinz.blog.service.SpiderService;
import com.hinz.blog.model.Article;
import com.hinz.blog.model.Spider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 *  爬虫规则管理
 * @author hinz
 */
@RestController
@RequestMapping("api/spider")
public class ApiSpiderController {

    @Autowired
    private SpiderService spiderService;

    @GetMapping
    public Result list(){
        return Result.success("查询成功",spiderService.list());
    }

    @PostMapping
    public Result save(@RequestBody Spider spider){
        ValidatorUtils.validate(spider);
        spiderService.saveOrUpdate(spider);
        return Result.success("保存成功",spider);
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",spiderService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id){
        return spiderService.removeById(id)?Result.success("删除成功"):Result.fail("删除失败");
    }

    @PostMapping("spiderArticle")
    public Result spiderArticle(Long id,String url){
        Spider spider=spiderService.getById(id);
        if(StringUtils.isBlank(url)){
            return Result.fail("文章链接不能为空");
        }
        if(spider==null){
            return Result.fail("规则不能为空");
        }
        Article article;
        try {
            article=spiderService.spiderArticle(spider,url);
        } catch (IOException e) {
            throw new BlogException(CodeEnum.SPIDER_ERROR.getValue(),"文章抓取失败",e);
        }
        return article!=null?Result.success("抓取成功",article):Result.fail("抓取失败");
    }
}

