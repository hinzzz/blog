package com.hinz.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinz.blog.model.BlogMember;

import java.util.List;

public interface BlogUserMapper extends BaseMapper<BlogMember> {

    List<BlogMember> selectAllUser();

}
