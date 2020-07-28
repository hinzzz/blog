package com.hinz.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinz.blog.common.constant.CodeEnum;
import com.hinz.blog.common.util.R;
import com.hinz.blog.model.Article;
import com.hinz.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author quanhz
 */
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    protected ArticleService articleService;

    @GetMapping()
    public R list(@RequestParam(value = "current") Integer current, @RequestParam(value = "size") Integer size,@RequestParam(value="cat",required = false)String cat){
        IPage<Article> page=articleService.findPageByUrl(new Page<>(current, size),cat);
        return R.ok().put("page",page);
    }

    @GetMapping("/likes/{articleId}/{operFlag}")
    public R likes(@PathVariable("articleId")Integer articleId,@PathVariable("operFlag")Integer operFlag){
        return articleService.likes(articleId,operFlag)>=0?R.ok(): R.error(CodeEnum.GOODS_ERROR.getValue(),"点赞失败");
    }

    @GetMapping("/collects/{articleId}/{operFlag}")
    public R collects(@PathVariable("articleId")Integer articleId,@PathVariable("operFlag")Integer operFlag){
        return articleService.collects(articleId,operFlag)>=0?R.ok(): R.error(CodeEnum.COLLECTS_ERROR.getValue(),"收藏失败");
    }
}
