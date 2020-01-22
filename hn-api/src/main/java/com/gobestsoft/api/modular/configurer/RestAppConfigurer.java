package com.gobestsoft.api.modular.configurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gobestsoft.api.config.Meshsite3Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

/**
 * @author gutongwei
 * <p>
 * 项目配置
 * <p>
 * 2017年11月23日
 */
@Configuration
public class RestAppConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*","https://localhost/")
                .allowCredentials(true)
                .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS")
                //解决跨域问题 Access-Control-Allow-Origin
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers","access-control-allow-methods",
                        "access-control-allow-origin","access-control-max-age","X-Frame-Options")
                .allowCredentials(false).maxAge(3600);
    }

    /**
     * 身份验证需要排除的接口
     */
    final String[] authExcludePath = new String[]{"/show-page/**",
            "/user/getMobileCode", "/user/register", "/user/login", "/user/loginByMobile", "/user/modifyPassword",
            "/information/list", "/information/matrix", "/information/h5Detail", "/information/getBanner","/information/getChannel",
            "/home","/homeV2", "/home/*", "/getDialog", "/information/hotSearch", "/information/topic", "/getDeptInfo",
            "/interact/groupDetail", "/interact/groups", "/interact/member", "/interact/groupsIndex",
            "/show/list",
            "/cky/acStation", "/cky/topicDetail", "/cky/topicReply", "/cky/studioDetail", "/cky/studios",
            "/course/list", "/cky/getHomePage", "/qyp/getHomePage", "/course/getHomePage",
            "/srv/parameters","/validAppVersion","/external/getSysUserByUid","/getDicts","/getBigData/**",
            "/yhzcxy","/lxwm","/czsc",
            "/lawApi/forum","/xlyz/**","/user/openApp","/user/sendMsgTest","/getSignOutTip",
            "/lawApi/commonProblem"
    };


    /**
     * 接口签名验证需要排除的积分
     */
    final String[] signExcludePath = new String[]{"/show-page/**","/external/getSysUserByUid","/getSignOutTip","/getBigData/**"};

    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    SignInterceptor signInterceptor() {
        return new SignInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor()).excludePathPatterns(signExcludePath).addPathPatterns("/**");
        registry.addInterceptor(authInterceptor()).excludePathPatterns(authExcludePath).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(false);
    }

    /**
     * SiteMesh 配置
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean siteMeshFilter() {
        FilterRegistrationBean fitler = new FilterRegistrationBean();
        Meshsite3Filter siteMeshFilter = new Meshsite3Filter();
        fitler.setFilter(siteMeshFilter);
        return fitler;
    }


    /**
     * SpringBoot配置自定义json解析
     * createTime gutongwei
     * 2018/6/18
     *
     * @return
     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        FastJsonConfig config = new FastJsonConfig();
//        List<MediaType> mediaTypes = new ArrayList<>();
//        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        //如果是null就返回null属性
//        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.SortField);
//        converter.setSupportedMediaTypes(mediaTypes);
//        converter.setFastJsonConfig(config);
//        return new HttpMessageConverters(converter);
//    }


    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }
}
