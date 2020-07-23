package com.hinz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.blog.mapper.CommentMapper;
import com.hinz.blog.model.enums.CommentStatusEnum;
import com.hinz.blog.service.CommentService;
import com.hinz.blog.model.Comment;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author hinz
 * @since 2020-07-13
 */
@Service
@CacheConfig(cacheNames = "comment")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public IPage<Comment> findPageByArticleId(Page<Comment> page,Long articleId) {
        IPage<Comment> commentPage=baseMapper.selectPage(page,new QueryWrapper<Comment>().eq("article_id",articleId)
                .eq("status", CommentStatusEnum.PUBLISHED.getValue()).eq("parent_id",0).orderByDesc("create_time"));

        List<Comment> allComments = baseMapper.selectList(new QueryWrapper<Comment>().eq("article_id", articleId)
                .eq("status", CommentStatusEnum.PUBLISHED.getValue()));
        //获得所有引用评论

        if(commentPage.getRecords()!=null && commentPage.getRecords().size()>0){
            for (Comment comment : commentPage.getRecords()) {
                comment.setComments(getReplyComments(comment,allComments));
            }
        }

        return commentPage;
    }

    private List<Comment> getReplyComments(Comment rootComment,List<Comment> allComments) {
        List<Comment> replyComments = allComments.stream().filter(entity ->
                entity.getParentId().intValue() == rootComment.getId().intValue())
                .map(replyComment -> {
                    replyComment.setComments(getReplyComments(replyComment, allComments));
                    return replyComment;
                }).sorted((c2, c1) -> {
                    long result = (c1.getCreateTime() == null ? 0 : c1.getCreateTime().getTime()) - (c2.getCreateTime() == null ? 0 : c2.getCreateTime().getTime());
                    return Integer.valueOf(result+"");
                }).collect(Collectors.toList());
        return replyComments;
    }

    @Override
    @Cacheable(key = "targetClass + methodName + #p0 + #p1")
    public List<Comment> findLatestComments(Integer count,boolean showCheck) {
        return baseMapper.selectLatestComments(count,showCheck);
    }

    @Override
    public IPage<Comment> findCommentsByPage(Page<Comment> page, QueryWrapper wrapper) {
        return baseMapper.selectCommentPage(page,wrapper);
    }

    @Override
    public Comment findCommentById(Long id) {
        return baseMapper.selectCommentById(id);
    }
}
