package com.hinz.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinz.blog.model.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分类 Mapper 接口
 * @author hinz
 * @since 2020-07-13
 */
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("select c.*,(select count(1) from blog_article where category_id=c.id and status=1) as total from blog_category c order by create_time asc")
    List<Category> findAllCategory();
}
