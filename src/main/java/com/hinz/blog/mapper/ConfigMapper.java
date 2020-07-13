package com.hinz.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinz.blog.model.Config;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 全局变量
 * @author hinz
 * @since 2020-07-13
 */
public interface ConfigMapper extends BaseMapper<Config> {
    @Update("update blog_config set `value`=#{value} where `name`=#{name}")
    int updateConfigByName(@Param("name")String name,@Param("value")String value);

    @Select("select value from blog_config where `name`=#{name}")
    String selectConfigByName(String name);
}
