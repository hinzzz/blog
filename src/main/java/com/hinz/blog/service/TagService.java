package com.hinz.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.blog.model.Tag;

import java.util.List;

/**
 * 标签服务类
 *
 * @author hinz
 * @since 2020-07-13
 */
public interface TagService extends IService<Tag> {
    /**
     * 查询热门标签
     * @param count 需要查询的数量
     * @return
     */
    List<Tag> findHotTags(Integer count);

    /**
     * 查询当前文章的标签
     * @param articleId 文章ID
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);
}
