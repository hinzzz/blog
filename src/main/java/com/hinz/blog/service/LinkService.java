package com.hinz.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.blog.model.Link;

import java.util.List;

/**
 * 链接服务类
 * @author hinz
 * @since 2020-07-13
 */
public interface LinkService extends IService<Link> {
    /**
     * 根据类型查询链接
     * @param type 链接类型
     * @return
     */
    List<Link> findLinkByType(Integer type);
}
