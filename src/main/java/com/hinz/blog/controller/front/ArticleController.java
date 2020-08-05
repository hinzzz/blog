package com.hinz.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinz.blog.common.constant.CodeEnum;
import com.hinz.blog.common.constant.Const;
import com.hinz.blog.common.exception.BlogException;
import com.hinz.blog.common.util.R;
import com.hinz.blog.model.Article;
import com.hinz.blog.model.Comment;
import com.hinz.blog.service.ArticleService;
import com.hinz.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author quanhz
 */
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    protected ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @GetMapping()
    public R list(@RequestParam(value = "current") Integer current,
                  @RequestParam(value = "size") Integer size,
                  @RequestParam(value="cat",required = false)String cat,
                  @RequestParam(value="q",required = false)String q){
        IPage<Article> page=articleService.findPageByUrl(new Page<>(current, size),cat,q);
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
            IPage<Comment> commentPage=commentService.findPageByArticleId(new Page<>(1, Const.COMMENT_SIZE),info.getId());
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
