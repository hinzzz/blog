package com.hinz.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinz.blog.common.constant.CodeEnum;
import com.hinz.blog.common.constant.Const;
import com.hinz.blog.common.exception.BlogException;
import com.hinz.blog.common.util.R;
import com.hinz.blog.model.enums.ArticleTypeEnum;
import com.hinz.blog.model.enums.LinkTypeEnum;
import com.hinz.blog.service.ArticleService;
import com.hinz.blog.service.CommentService;
import com.hinz.blog.service.LinkService;
import com.hinz.blog.service.TagService;
import com.hinz.blog.model.Article;
import com.hinz.blog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台首页控制器
 * @author hinz
 * @date 2020-07-13
 */
@RestController
public class IndexController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;


    /**
     * 文章详情页
     * @return
     */
    @ResponseBody
    @GetMapping("{url}.html")
    public R index(Model model, @PathVariable(value = "url") String url) {
        Article info=articleService.findArticleByUrl(url);
        if(null==info){
            throw new BlogException(CodeEnum.NOT_FOUND.getValue(),"文章不存在："+url);
        }
        model.addAttribute("info",info);
        //查询当前文章的第一页评论
        if(info.isComment()){
            IPage<Comment> commentPage=commentService.findPageByArticleId(new Page<>(1,Const.COMMENT_SIZE),info.getId());
            model.addAttribute("comments",commentPage);
        }
        //上一篇和下一篇
        Article previousArticle=articleService.findPreviousById(info.getId());
        model.addAttribute("previousArticle",previousArticle);
        Article nextArticle=articleService.findNextById(info.getId());
        model.addAttribute("nextArticle",nextArticle);

        return R.ok().put("article",info);
    }
}
