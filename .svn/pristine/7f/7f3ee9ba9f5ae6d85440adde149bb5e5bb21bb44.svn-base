package com.gobestsoft.mamage.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.gobestsoft.mamage.dialect.AuthorityDialect;
import com.gobestsoft.mamage.dialect.FormDialect;
import com.gobestsoft.mamage.dialect.SearchDialect;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * 项目配置
 * create by gutongwei
 * on 2018/7/27 下午4:17
 */
@Configuration
public class ManageConfigurer extends WebMvcConfigurationSupport {


    /**
     * 验证排除
     */
    protected final String[] authExcludePath = new String[]{
            "/login", "/company", "/company/**", "/kaptcha/**",
            "/loginVail","/register","/registerVail","/activate","/orgDetail",
            "/layout","/static","/third-party/**","/forget", "/getEmailCode",
            "/getDefaultKaptcha", "/getResetPwd", "/release/**", "/jifen/**",
            "/changePwdPage"
    };


    /**
     * 验证码生成相关
     */
    @Bean
    public DefaultKaptcha kaptcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "blue");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "50");
        properties.put("kaptcha.textproducer.font.size", "40");
        properties.put("kaptcha.session.key", "code");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        properties.put("kaptcha.textproducer.char.string", "1234567890");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean
    public SearchDialect getSearchDialect() {
        SearchDialect searchDialect = new SearchDialect();
        return searchDialect;
    }


    @Bean
    public AuthorityDialect getDefalultDialect() {
        return new AuthorityDialect();
    }

    @Bean
    public FormDialect getFormDialect() {
        return new FormDialect();
    }


    /**
     * SpringBoot配置自定义json解析
     * createTime gutongwei
     * 2018/6/18
     *
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //如果是null就返回null属性
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                                    SerializerFeature.WriteNullStringAsEmpty,
                                    SerializerFeature.WriteNullNumberAsZero,
                                    SerializerFeature.SortField,
                                    SerializerFeature.IgnoreNonFieldGetter);

        converter.setSupportedMediaTypes(mediaTypes);
        converter.setFastJsonConfig(config);
        return new HttpMessageConverters(converter);
    }


    /**
     * 配置文件上传
     * @return
     */
    @Bean(name="multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(209715200);
        multipartResolver.setMaxInMemorySize(10485760);
        return multipartResolver;
    }

}
