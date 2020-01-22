package com.gobestsoft.mamage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.gobestsoft.core.util.ToolUtil.getTempPath;
import static com.gobestsoft.core.util.ToolUtil.isEmpty;

/**
 * create by gutongwei
 * on 2018/7/30 上午9:19
 */

@Component
@ConfigurationProperties(prefix = ManageProperties.PREFIX)
@Data
public class ManageProperties {
    public static final String PREFIX = "manage";

    private Boolean kaptchaOpen = false;

    private String fileUploadPath;

    private Boolean haveCreatePath = false;

    private String passwordSalt;

    private String ueditorConfigFile;

    private String articleAccess;

    private boolean writeLog;

    public String getFileUploadPath() {
        //如果没有写文件上传路径,保存到临时目录
        if (isEmpty(fileUploadPath)) {
            return getTempPath();
        } else {
            //判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }
            //判断目录存不存在,不存在得加上
            if (haveCreatePath == false) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }
}
