package com.hinz.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.blog.model.Config;

import java.util.Map;

/**
 * 配置服务类
 * @author hinz
 * @since 2020-07-13
 */
public interface ConfigService extends IService<Config> {
    /**
     * 获得所有全局变量
     * @return 转换成Map之后的变量
     */
    Map<String, String> findAllGlobal();

    /**
     * 清除配置缓存
     */
    void clearCache();

    /**
     * 根据参数名获得指定参数
     * @return
     */
    String findByName(String name);

    /**
     * 根据参数名，获取参数值的Object对象
     * @param name    参数名
     * @param clazz  Object对象
     */
    <T> T getConfigObject(String name, Class<T> clazz);
}
