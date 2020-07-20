package com.hinz.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.blog.common.util.Result;
import com.hinz.blog.model.BlogMember;


public interface BlogUserService extends IService<BlogMember> {
    Result login(BlogMember blogMember);
}
