package com.gobestsoft.api.modular.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gobestsoft.api.config.cache.RedisCacheFactory;
import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.core.basic.HttpBasic;
import com.gobestsoft.core.reids.RedisCacheModel;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.FileUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口请求基类
 * Create by gutongwei
 * 2018/5/18
 */
@Slf4j
public class RestBasic extends HttpBasic {

    @Autowired
    protected ApiProperties apiProperties;

    @Autowired
    protected RedisCacheFactory cacheFactory;

    @Autowired
    protected RedisUtils redisUtils;

    protected String HEADER_TOKEN_NAME = "token";

    protected String HEADER_DEVICEID_NAME = "device_id";

    protected BaseResult success() {
        return new BaseResult(200, null, null);
    }

    protected BaseResult success(Object data) {
        return new BaseResult(200, null, data);
    }

    protected BaseResult baseResult(int code, String message, Object data) {
        return new BaseResult(code, message, data);
    }

    protected BaseResult fail() {
        return new BaseResult(BaseResult.ResultCode.ERROR500, null);
    }

    protected BaseResult fail(String message) {
        return new BaseResult(500, message, null);
    }

    protected BaseResult fail(BaseResult.ResultCode code) {
        return new BaseResult(code, null);
    }

    /**
     * 保存文件
     *
     * @param uploadConstantsPath
     * @param file
     * @return 如果文件未上传返回null
     * @throws IOException
     */
    protected FileUpload saveFile(String uploadConstantsPath, MultipartFile file, boolean withName) throws IOException {
        // 判断文件是否为空
        if (file != null && !file.isEmpty()) {
            String relativeDir = uploadConstantsPath + DateUtil.getDays() + "/";
            String savePath = apiProperties.getFileUploadPath() + "/" + relativeDir;
            log.debug("保存文件：地址为：" + savePath);
            File templateFile = new File(savePath);
            // 判断目标文件所在的目录是否存在
            if (!templateFile.getParentFile().exists()) {
                templateFile.getParentFile().mkdirs();
            }
            // 判断上传文件的保存目录是否存在
            if (!templateFile.exists() && !templateFile.isDirectory()) {
                templateFile.mkdir();
            }
            // 获取item中的上传文件的输入流
            InputStream in = file.getInputStream();
            // 创建一个文件输出流
            String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
            String saveFileName = UUIDUtil.getUUID() + "." + suffix;
            if (withName) {
                saveFileName = file.getOriginalFilename();
                savePath = savePath + "/" + UUIDUtil.getUUID();
            }
            String savePathAndName = savePath + saveFileName;
            OutputStream os = new FileOutputStream(new File(savePathAndName));
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            in.close();
            os.flush();
            return new FileUpload(file.getOriginalFilename(),
                    relativeDir + saveFileName,
                    WebSiteUtil.getBrowseUrl(relativeDir + saveFileName));
        }
        return null;
    }


    /**
     * 保存文件
     *
     * @param uploadConstantsPath
     * @param files
     * @return 如果有files中有获取不到文件，返回的list中item未null
     */
    protected List<FileUpload> saveFiles(String uploadConstantsPath, MultipartFile... files) throws IOException {
        List<FileUpload> result = new ArrayList<>();
        for (MultipartFile file : files) {
            FileUpload fileUpload = saveFile(uploadConstantsPath, file, false);
            if (fileUpload != null) {
                result.add(fileUpload);
            }
        }
        return result;
    }


    /**
     * 读取appConfig.json 获取配置文件属性
     *
     * @param key
     * @return
     */
    protected Object getAppConfig(String key) {
        RedisCacheModel cacheModel = cacheFactory.getCacheModel(CacheConstant.APP_CONFIG_JSON);
        JSONObject result = null;
        if (cacheModel == null || cacheModel.isExpired()) {
            try {
                File configFile = ResourceUtils.getFile("classpath:appConfig.json");
                String appConfigJson = FileUtil.getFileContent(configFile);
                result = JSON.parseObject(appConfigJson);
                cacheModel = new RedisCacheModel(appConfigJson, result.getIntValue("cacheSecond"));
                redisUtils.set(CacheConstant.APP_CONFIG_JSON, cacheModel);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            result = JSON.parseObject(cacheModel.getData().toString());
        }
        return result.get(key);
    }

}
