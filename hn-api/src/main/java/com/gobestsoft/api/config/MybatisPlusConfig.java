package com.gobestsoft.api.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisPlus配置
 *
 * @author gobestsoft
 * @Date 2017年8月23日12:51:41
 */
@Configuration
@EnableTransactionManagement
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
