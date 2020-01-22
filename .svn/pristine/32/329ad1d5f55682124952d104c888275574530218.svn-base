package com.gobestsoft.mamage.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 *
 * @author gobestsoft
 * @Date 2017/5/20 21:58
 */
@Configuration
@MapperScan(basePackages = {"com.gobestsoft.common.modular.*.mapper", "com.gobestsoft.common.modular.*.dao"})
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
