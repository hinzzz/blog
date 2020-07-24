package com.hinz.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hinz.blog.common.constant.ConfigConst;
import com.hinz.blog.common.constant.Const;
import com.hinz.blog.common.util.IPUtils;
import com.hinz.blog.common.util.R;
import com.hinz.blog.common.util.Result;
import com.hinz.blog.model.Article;
import com.hinz.blog.model.enums.CommentStatusEnum;
import com.hinz.blog.service.ArticleService;
import com.hinz.blog.service.CommentService;
import com.hinz.blog.service.ConfigService;
import com.hinz.blog.model.Comment;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 前台评论控制器
 * @author hinz
 * @date 2020-07-13
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ArticleService articleService;

    @GetMapping()
    public R list(@RequestParam(value = "articleUrl",required = true)String articleUrl,@RequestParam("current")Long current,@RequestParam("size")Long size){
        articleUrl = articleUrl.substring(0,articleUrl.lastIndexOf("."));
        Article article = articleService.findArticleByUrl(articleUrl);
        if(article!=null && article.isComment()){
            IPage<Comment> commentPage=commentService.findPageByArticleId(new Page<>(current, size),article.getId());
            return R.ok().put("comment",commentPage);
        }
        return R.ok();
    }

    /**
     * 加载评论
     * @param current 需要加载的页数
     * @param size 需要加载的数量
     * @param articleId 文章ID
     * @return
     */
    @PostMapping("more")
    public List<Comment> commentPage(int current, int size, long articleId){
        return commentService.findPageByArticleId(new Page<>(current,size,false),articleId).getRecords();
    }

    /**
     * 评论文章
     * @param comment
     * @return
     */
    @PostMapping("article")
    public R commentArticle(@RequestBody Comment comment, HttpServletRequest request){
        if(StringUtils.isBlank(comment.getContent())){
            return R.error("请输入评论内容");
        }
        if(StringUtils.isBlank(comment.getAuthor())){
            comment.setAuthor("匿名用户");
        }
        if(StringUtils.isNotBlank(comment.getEmail())){
            comment.setEmailMd5(DigestUtils.md5Hex(comment.getEmail()));
        }
        comment.setUserAgent(request.getHeader("user-agent"));
        String ip= IPUtils.getIpAddr(request);
        comment.setIp(ip);
        comment.setAdmin(false);
        if(Boolean.parseBoolean(configService.findByName(ConfigConst.COMMENT_CHECK))){
            comment.setStatus(CommentStatusEnum.CHECKING.getValue());
        }else{
            comment.setStatus(CommentStatusEnum.PUBLISHED.getValue());
        }
        boolean save = commentService.save(comment);
        if(save){

            //更新评论次数
            int commentsResult = articleService.updateForCommentsById(comment.getArticleId());
            if(commentsResult<=0){
                return R.error("更新评论次数失败");
            }
            comment = commentService.findCommentById(comment.getId());
            comment.setComments(new ArrayList<>());
        }
        return save?R.ok().put("comment",comment):R.error("评论失败");
    }
}
