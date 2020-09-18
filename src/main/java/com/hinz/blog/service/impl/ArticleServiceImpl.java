package com.hinz.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.blog.mapper.ArticleMapper;
import com.hinz.blog.model.Article;
import com.hinz.blog.model.Catalogue;
import com.hinz.blog.service.ArticleService;
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
 *
 * @author hinz
 * @since 2020-07-13
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Article findArticleByUrl(String url) {
        Article article = baseMapper.selectArticleByUrl(url);
        //每查询一次，浏览次数+1
        if (article != null) {
            baseMapper.updateForVisitsById(article.getId());
        }
        return article;
    }

    @Override
    public IPage<Article> findPageByUrl(Page<Article> page, String url, String q) {
        try {
            page.setDesc("id");
            return baseMapper.findPageByUrl(page, url, q);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询文章失败", e);
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
        keyword = StringUtils.isBlank(keyword) ? null : '%' + keyword + '%';
        return baseMapper.selectArticleList(page, keyword);
    }

    @Override
    public IPage<Article> findPageByTag(Page<Article> page, Long tagId) {
        page.setDesc("id");
        return baseMapper.selectListByTag(page, tagId);
    }

    @Override
    public IPage<Article> findPageByCategory(Page<Article> page, Long categoryId) {
        page.setDesc("id");
        return baseMapper.selectListByCategory(page, categoryId);
    }

    @Override
    @Cacheable(value = "article", key = "targetClass + methodName + #count")
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
    public int likes(Integer articleId, Integer operFlag) {
        //TODO 增加到用户的点赞列
        if (operFlag != null) {
            int oper = 0;
            if (operFlag == 0) {
                oper = -1;
            } else if (operFlag == 1) {
                oper = 1;
            }
            return baseMapper.likes(articleId, oper);
        }
        return 0;
    }

    @Override
    public int collects(Integer articleId, Integer operFlag) {
        //TODO 增加到用户的收藏列
        if (operFlag != null) {
            int oper = 0;
            if (operFlag == 0) {
                oper = -1;
            } else if (operFlag == 1) {
                oper = 1;
            }
            return baseMapper.collects(articleId, oper);
        }
        return 0;
    }


    @Override
    public String generateCatalogue(String content) {
        
        if (StringUtils.isBlank(content)) {
            return "";
        }
        String p = "<h\\d>.*</h\\d>";
        String levelP = "<h\\d>";
        String realTitleP = "</a>.*</h\\d>";
        Pattern r = Pattern.compile(p);
        Pattern levelR = Pattern.compile(levelP);
        Pattern realTitleR = Pattern.compile(realTitleP);
        Matcher m = r.matcher(content);
        List<Catalogue> catalogues = new ArrayList<>();

        while (m.find()) {
            Catalogue c = new Catalogue();
            //<h4><a id="6nosql_74"></a>6、nosql数据库四大分类</h4>
            String sourceTitle = m.group(0);
            c.setSourceTitle(sourceTitle);
            Matcher levelM = levelR.matcher(sourceTitle);
            if (levelM.find()) {
                //<h4>
                String hTag = levelM.group(0);
                c.setLevel(Integer.valueOf(hTag.substring(2,3)));
            }
            Matcher realTitleM = realTitleR.matcher(sourceTitle);
            if (realTitleM.find()) {
                String realTitle = realTitleM.group(0);
                realTitle = realTitle.substring(4, realTitle.length() - 5);
                c.setTitle(realTitle);
            }
            catalogues.add(c);
        }
        List<Catalogue> result = new ArrayList<>();
        genCatalogueList(0,catalogues,result);
        StringBuilder sb = new StringBuilder();
        for (Catalogue catalogue : catalogues) {
            sb.append(catalogue.getSourceTitle());
        }

        //return JSONObject.toJSON(result).toString();
        return sb.toString();
    }

    private List<Catalogue> genCatalogueList(int index, List<Catalogue> catalogues, List<Catalogue> result) {

        Catalogue root = catalogues.get(index);
        List<Catalogue> childs = new ArrayList<>();
        Integer rootLevel = root.getLevel();
        for (int i = index + 1; i < catalogues.size(); i++) {
            Catalogue temp = catalogues.get(i);
            Integer level = temp.getLevel();
            //如果当前标题层级大于或等于父标题 重新取当前标题的子标题
            if (level <= rootLevel) {
                genCatalogueList(i, catalogues, result);
                break;
            }
            childs.add(temp);
        }
        root.setChilds(childs);
        result.add(root);
        return result;
    }


}
