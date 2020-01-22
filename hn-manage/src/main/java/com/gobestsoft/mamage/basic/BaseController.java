package com.gobestsoft.mamage.basic;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.core.util.*;
import com.gobestsoft.mamage.basic.page.PageInfoBT;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.constant.state.Order;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * create by gutongwei
 * on 2018/7/27 下午5:52
 */
public class BaseController extends ManageBasic {

    protected final String LOGIN_TIP = "tip";

    /**
     * 成功数据
     *
     * @return
     */
    protected Tip success() {
        return new Tip(200, null, null);
    }
    /**
     * 成功数据
     * @return
     */
    protected Tip success(Object data) {
        return new Tip(200, null, data);
    }

    /**
     * 失败数据
     * @return
     */
    protected Tip fail() {
        return new Tip(500, "服务器异常", null);
    }

    /**
     * 失败数据
     * @return
     */
    protected Tip fail(String message) {
        return new Tip(500, message, null);
    }

    /**
     * 失败数据
     * @return
     */
    protected Tip tip(int code, String message, Object data) {
        return new Tip(code, message, data);
    }


    /**
     * 把service层的分页信息，封装为bootstrap table通用的分页封装
     */
    protected <T> PageInfoBT<T> packForBT(Page<T> page) {
        return new PageInfoBT<T>(page);
    }

    /**
     * 包装一个list，让list增加额外属性
     */
    protected Object warpObject(BaseControllerWrapper warpper) {
        return warpper.wrap();
    }


    /**
     * 获取默认分页
     *
     * @return
     */
    public Page defaultPage() {
        HttpServletRequest request = getHttpServletRequest();
        int limit = 10;
        int offset = 0;
        if (StringUtils.isNumeric(request.getParameter("limit"))) {
            limit = Integer.valueOf(request.getParameter("limit"));
        }
        if (StringUtils.isNumeric(request.getParameter("offset"))) {
            offset = Integer.valueOf(request.getParameter("offset"));
        }
        String sort = request.getParameter("sort");         //排序字段名称
        String order = request.getParameter("order");       //asc或desc(升序或降序)
        if (ToolUtil.isEmpty(sort)) {
            Page page = new Page((offset / limit + 1), limit);
            page.setOpenSort(false);
            return page;
        } else {
            Page page = new Page((offset / limit + 1), limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }


    /**
     * base64字符保存图片
     *
     * @param imageStr
     * @param rootPath
     * @param folderName
     * @return
     * @throws IOException
     */
    protected String base64TransferImage(String imageStr, String rootPath, String folderName) throws IOException {
        if (StringUtils.isEmpty(imageStr)) {
            return "";
        }
        if (StringRegularUtil.isURL(imageStr)) {
            return imageStr;
        }

        String img = imageStr.replace("data:image/jpeg;base64,", "");
        String imagePath = FileUtil.createCover(img, rootPath, folderName);
        return imagePath;
    }

    /**
     * base64字符保存图片
     *
     * @param imageStrs
     * @param rootPath
     * @param folderName
     * @return
     * @throws IOException
     */
    protected String base64TransferImage(String[] imageStrs, String rootPath, String folderName) throws IOException {
        String imagePath = "";
        for (String imageStr : imageStrs) {
            if (StringUtils.isNotEmpty(imageStr)) {
                imagePath += base64TransferImage(imageStr, rootPath, folderName) + ",";
            }
        }
        if (StringUtils.isNotEmpty(imagePath)) {
            return imagePath.substring(0, imagePath.length() - 1);
        }
        return "";
    }

    /***
     * 上传单个文件
     *
     * @param file
     * @return
     */
    public String fileUpload(MultipartFile file, String uploadConstants) throws IOException {
        return saveFile(uploadConstants, file);
    }


    /**
     * 保存文件
     *
     * @param uploadConstantsPath
     * @param file
     * @return 如果文件未上传返回null
     * @throws IOException
     */
    protected String saveFile(String uploadConstantsPath, MultipartFile file) throws IOException {
        // 判断文件是否为空
        if (file != null && !file.isEmpty()) {
            String relativeDir = uploadConstantsPath + DateUtil.getDays() + "/";
            String savePath = manageProperties.getFileUploadPath() + "/" + relativeDir;
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
//            templateFile.delete();
            return relativeDir + saveFileName;
        }
        return null;
    }


    /**
     * 移除map中的键值对
     */
    public void removeValue(Map map, String key){
        if(map!=null&&map.containsKey(key)){
            map.remove(key);
        }
    }
    public void removeValue(List<Map> list,String key){
        for(Map m:list){
            removeValue(m,key);
        }
    }
    public void removeValue(List<Map> list,String[] keys){
        if(keys==null|| keys.length==0){
            return;
        }
        for(Map<String,Object> m:list){
            Iterator<Map.Entry<String,Object>> iterator = m.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                for(String key:keys){
                    if(key.equals(next.getKey())){
                        iterator.remove();
                    }
                }

            }
        }
    }

    /**
     * 处理后台日期为正常格式
     * @param oldDateStr
     * @param newPattern
     * @return
     */
    public String getNortDate(String oldDateStr,String newPattern){
        if(StringUtils.isEmpty(oldDateStr)){
            return "";
        }
        if(StringUtils.isEmpty(newPattern)){
            newPattern  = "yyyy-MM-dd";
        }

        return DateUtil.parseAndFormat(oldDateStr,"yyyyMMddHHmmss",newPattern);
    }
    public String getNortDate(String oldDateStr){
        return getNortDate(oldDateStr,null);
    }

}
