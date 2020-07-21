package com.hinz.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinz.blog.model.BlogFrontUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogFrontUserMapper extends BaseMapper<BlogFrontUser> {

    @Select("select * from blog_user")
    List<BlogFrontUser> selectAllUser();

}
