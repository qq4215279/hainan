package com.gobestsoft.api.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 项目相关配置
 *
 * @author gobestsoft
 * @date 2017年10月23日16:44:15
 */
@Configuration
@ConfigurationProperties(prefix = ApiProperties.REST_PREFIX)
@Data
@Component
public class ApiProperties {

    protected static final String REST_PREFIX = "api";

    /**
     * 上传文件地址
     */
    private String fileUploadPath;


    /**
     * 接口加密关键字
     */
    private String salt;

    /**
     * 接口基地址
     */
    private String apiWebSite;

    /**
     * 接口签名验证是否打开
     */
    private boolean openSign;

    /**
     * 安卓apk下载地址
     */
    private String apkDownload;

    /**
     * IOS itunes下载地址
     */
    private String appStoreLink;

    /**
     * 访问资讯
     */
    private String reviewBaseUrl;


    /**
     * 短信范围次数
     */
    private int mobileMostTime;

    /**
     * 课程访问路径
     */
    private String courseUrl;

    /**
     * 当日积分上限
     */
    private int upperIntegral;


    private String matrixLogo;

    /**
     * 下载地址
     */
    private String downloadUrl;


    private String h5Parameter;


    private String phpSite;

    /**
     * 日志输出
     */
    private boolean writeLogs;

    /**
     * 资讯访问路径
     *
     * @param articleId
     * @return
     */
    public String getReviewBaseUrl(String articleId, boolean needParam) {
        if (needParam) {
            return reviewBaseUrl + articleId + h5Parameter;
        }
        return reviewBaseUrl + articleId;
    }

    /**
     * 访问课程路径
     *
     * @param courseId
     * @return
     */
    public String getCourseUrl(String courseId, boolean needParam) {
        if (needParam) {
            return this.courseUrl + courseId + h5Parameter;
        }
        return this.courseUrl + courseId;
    }

}
