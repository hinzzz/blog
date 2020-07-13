package com.hinz.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.blog.model.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author hinz
 * @since 2020-07-13
 */
public interface MenuService extends IService<Menu> {
    /**
     * 获得所有菜单
     * @return 菜单集合
     */
    List<Menu> findAllMenu();
}
