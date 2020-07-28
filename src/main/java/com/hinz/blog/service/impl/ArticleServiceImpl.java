package com.hinz.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.blog.mapper.ArticleMapper;
import com.hinz.blog.service.ArticleService;
import com.hinz.blog.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章服务实现类
 * @author hinz
 * @since 2020-07-13
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Article findArticleByUrl(String url) {
        Article article=baseMapper.selectArticleByUrl(url);
        //每查询一次，浏览次数+1
        if(article!=null){
            baseMapper.updateForVisitsById(article.getId());
        }
        return article;
    }

    @Override
    public IPage<Article> findPageByUrl(Page<Article> page, String url) {
        try {
            page.setDesc("id");
            return baseMapper.findPageByUrl(page,url);
        }catch (Exception e){
            log.error("查询文章失败",e);
        }
        return null;
    }

    @Override
    public List<Article> findAllTopArticles() {
        return baseMapper.selectTopArticles();
    }

    @Override
    public IPage<Article> findPageByKeyword(Page<Article> page, String keyword) {
        page.setDesc("id");
        keyword=StringUtils.isBlank(keyword)?null:'%'+keyword+'%';
        return baseMapper.selectArticleList(page,keyword);
    }

    @Override
    public IPage<Article> findPageByTag(Page<Article> page, Long tagId) {
        page.setDesc("id");
        return baseMapper.selectListByTag(page,tagId);
    }

    @Override
    public IPage<Article> findPageByCategory(Page<Article> page, Long categoryId) {
        page.setDesc("id");
        return baseMapper.selectListByCategory(page,categoryId);
    }

    @Override
    @Cacheable(value = "article",key = "targetClass + methodName + #count")
    public List<Article> findHotArticles(Integer count) {
        return baseMapper.selectHotArticles(count);
    }

    @Override
    public List<Article> findRandomArticles(Integer count) {
        return baseMapper.selectRandomArticles(count);
    }

    @Override
    public Article findPreviousById(Long id) {
        return baseMapper.selectPreviousById(id);
    }

    @Override
    public Article findNextById(Long id) {
        return baseMapper.selectNextById(id);
    }

    @Override
    public List<Article> findLatestArticle(int number) {
        return baseMapper.selectLatestArticle(number);
    }

    @Override
    public int updateForCommentsById(Long articleId) {
        return baseMapper.updateForCommentsById(articleId);
    }

    @Override
    public int likes(Integer articleId,Integer operFlag) {
        //TODO 增加到用户的点赞列
        if(operFlag!=null){
            int oper = 0;
            if(operFlag==0){
                oper = -1;
            }else if(operFlag==1){
                oper = 1;
            }
            return baseMapper.likes(articleId,oper);
        }
        return 0;
}

    @Override
    public int collects(Integer articleId,Integer operFlag) {
        //TODO 增加到用户的收藏列
        if(operFlag!=null){
            int oper = 0;
            if(operFlag==0){
                oper = -1;
            }else if(operFlag==1){
                oper = 1;
            }
            return baseMapper.collects(articleId,oper);
        }
        return 0;
    }
}
