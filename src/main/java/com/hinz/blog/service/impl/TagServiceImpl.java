package com.hinz.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.blog.mapper.TagMapper;
import com.hinz.blog.service.TagService;
import com.hinz.blog.model.Tag;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签服务实现类
 * @author hinz
 * @since 2020-07-13
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    @Cacheable(value = "tag",key = "targetClass + methodName + #count")
    public List<Tag> findHotTags(Integer count) {
        return baseMapper.selectHotTags(count);
    }

    @Override
    public List<Tag> findTagsByArticleId(Long articleId) {
        return baseMapper.selectTagsByArticleId(articleId);
    }
}
