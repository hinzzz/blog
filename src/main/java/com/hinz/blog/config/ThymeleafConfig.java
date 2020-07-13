package com.hinz.blog.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Thymeleaf配置
 * @author hinz
 * @date 2020/07/13
 */
@Configuration
public class ThymeleafConfig{

    /**
     * 开启Thymeleaf Layout Dialect支持
     * @return
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect(new GroupingStrategy());
    }
}
