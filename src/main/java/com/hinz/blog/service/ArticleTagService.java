package com.hinz.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.blog.model.ArticleTag;

import java.util.List;

/**
 * <p>
 * 文章与标签的对应关系 服务类
 * </p>
 *
 * @author hinz
 * @since 2020-07-13
 */
public interface ArticleTagService extends IService<ArticleTag> {
    /**
     * 保存指定文章的所有标签
     * @param articleId 文章ID
     * @param tagIds 标签ID集合
     * @return
     */
    boolean saveBatch(Long articleId, List<Long> tagIds);
}
