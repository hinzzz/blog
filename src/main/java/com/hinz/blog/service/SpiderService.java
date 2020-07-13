package com.hinz.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.blog.model.Article;
import com.hinz.blog.model.Spider;

import java.io.IOException;

/**
 * 爬虫规则服务类
 * @author hinz
 * @since 2020-07-13
 */
public interface SpiderService extends IService<Spider> {
    /**
     * 抓取文章
     * @param spider 抓取规则
     * @param url 抓取的链接
     * @return 抓取到的文章
     */
    Article spiderArticle(Spider spider,String url) throws IOException;
}
