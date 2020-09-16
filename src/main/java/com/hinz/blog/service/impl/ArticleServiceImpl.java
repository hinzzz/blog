package com.hinz.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.blog.mapper.ArticleMapper;
import com.hinz.blog.model.Catalogue;
import com.hinz.blog.service.ArticleService;
import com.hinz.blog.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public IPage<Article> findPageByUrl(Page<Article> page, String url,String q) {
        try {
            page.setDesc("id");
            return baseMapper.findPageByUrl(page,url,q);
        }catch (Exception e){
            e.printStackTrace();
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


    @Override
    public String generateCatalogue(String content) {
        //<h3><a id="NoSql__0"></a>NoSql 简介</h3>
        //<h4><a id="1_4"></a>1、概述</h4>
        //<h4><a id="2NosqlRDBMS_10"></a>2、Nosql与关系型数据库（RDBMS）的区别</h4>
        //<h4><a id="3NoSql__27"></a>3、NoSql 特性</h4>
        //<h4><a id="4nosqlnosqlsql_33"></a>4、当下nosql的经典应用（nosql和sql一起使用）</h4>
        //<h4><a id="5NoSql_45"></a>5、NoSql数据模型</h4>
        //<h4><a id="6nosql_74"></a>6、nosql数据库四大分类</h4>
        if(StringUtils.isBlank(content)){
            return "";
        }
        String p = "<h\\d>.*</h\\d>";
        String levelP = "<h\\d>";
        String realTitleP = "<h>.*</h\\d>";
        Pattern r = Pattern.compile(p);
        Pattern levelR = Pattern.compile(levelP);
        Pattern realTitleR = Pattern.compile(levelP);
        Matcher m = r.matcher(content);
        List<Catalogue> catalogues = new ArrayList<>();

        while (m.find()){
            Catalogue c = new Catalogue();
            String sourceTitle = m.group(0);
            c.setSourceTitle(sourceTitle);
            Matcher levelM = levelR.matcher(sourceTitle);
            if(levelM.find()){
                String hTag = levelM.group(0);
                c.setLeve(hTag.indexOf(2));
            }
            Matcher realTitleM = realTitleR.matcher(sourceTitle);
            if(realTitleM.find()){
                String realTitle = realTitleM.group(0);
                realTitle = realTitle.substring(4, realTitle.length()-5);
                c.setTitle(realTitle);
            }
            catalogues.add(c);
        }
        Catalogue root = catalogues.get(0);
        for (int i = 1; i < catalogues.size(); i++) {
            Catalogue temp = catalogues.get(i);

        }


        return null;
    }
}
