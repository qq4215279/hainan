package com.gobestsoft.api.modular.basic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.api.modular.appuser.model.AppUserDto;
import com.gobestsoft.api.modular.appuser.service.AppUserService;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/**
 * 接口返回数据格式
 *
 * @author gutongwei
 */
@Slf4j
public class BaseController extends RestBasic {

    @Autowired
    protected AppUserService appUserService;


    /**
     * 用户用户ID
     *
     * @return
     */
    public String getUserId() {
        AppUserDto appUserDto = getUserDto();
        if (appUserDto != null) {
            return getUserDto().getAuid();

        }
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public AppUserDto getUserDto() {
        String token = getToken();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return appUserService.getUserInfoByToken(token);
    }

    /**
     * 获取header中token值
     *
     * @return
     */
    public String getToken() {
        return getHeader(HEADER_TOKEN_NAME);
    }

    /**
     * 获取header中设备ID值
     *
     * @return
     */
    public String getDeviceId() {
        return getHeader(HEADER_DEVICEID_NAME);
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        if (StringUtils.isEmpty(getToken())) {
            return false;
        }
        if (getUserDto() != null) {
            return true;
        }
        return false;
    }

    /**
     * 是否已绑定会员
     * create by gutongwei
     * 2018/6/11
     *
     * @return
     */
    public boolean isBindMember() {
        if (isLogin() && getUserDto().getMember_info() != null) {
            return true;
        }
        return false;
    }


    /***
     * 上传单个文件
     *
     * @param file
     * @return
     */
    public FileUpload fileUpload(MultipartFile file, String uploadConstants) throws IOException {
        FileUpload result = saveFile(uploadConstants, file, false);
        return result;
    }

    /***
     * 上传单个文件
     *
     * @param file
     * @return
     */
    public FileUpload fileUploadWithName(MultipartFile file, String uploadConstants) throws IOException {
        FileUpload result = saveFile(uploadConstants, file, true);
        return result;
    }

    /**
     * 上传多个文件返回
     *
     * @param httpServletRequest
     * @param uploadConstants
     * @return
     * @throws IOException
     */
    public List<FileUpload> filesUpload(final HttpServletRequest httpServletRequest, String uploadConstants) throws IOException {
        return filesUpload(httpServletRequest, uploadConstants, false, null);
    }

    /**
     * 上传多个文件返回
     *
     * @param httpServletRequest
     * @param uploadConstants
     * @return
     * @throws IOException
     */
    public List<FileUpload> filesUploadWithName(final HttpServletRequest httpServletRequest, String uploadConstants) throws IOException {
        return filesUpload(httpServletRequest, uploadConstants, true, null);
    }

    /**
     * 上传多个文件返回
     *
     * @param httpServletRequest
     * @param uploadConstants
     * @param excludeNames       排除的文件
     * @return
     * @throws IOException
     */
    public List<FileUpload> filesUpload(final HttpServletRequest httpServletRequest, String uploadConstants, boolean withName, String... excludeNames) throws IOException {
        MultipartHttpServletRequest request = transferMultipartHttp(httpServletRequest);
        if (request != null) {
            List<FileUpload> result = new ArrayList<>();

            SortedMap<String, MultipartFile> sortedMap = new TreeMap<>();
            sortedMap.putAll(request.getFileMap());

            Iterator<Map.Entry<String, MultipartFile>> itr = sortedMap.entrySet().iterator();
            if (excludeNames == null) {
                excludeNames = new String[]{};
            }
            while (itr.hasNext()) {
                Map.Entry<String, MultipartFile> fileEntry = itr.next();
                if (!Arrays.asList(excludeNames).contains(fileEntry.getKey())) {
                    MultipartFile multipartFile = fileEntry.getValue();
                    FileUpload fileUpload = saveFile(uploadConstants, multipartFile, withName);
                    if (fileUpload != null) {
                        result.add(fileUpload);
                    }
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 根据传入的文件名模糊获取提交的文件
     *
     * @param httpServletRequest
     * @param uploadConstants
     * @return
     * @throws IOException
     */
    public List<FileUpload> filesUploadInclude(final HttpServletRequest httpServletRequest, String uploadConstants, String pattenName) throws IOException {
        MultipartHttpServletRequest request = transferMultipartHttp(httpServletRequest);
        if (request != null) {
            List<FileUpload> result = new ArrayList<>();


            SortedMap<String, MultipartFile> sortedMap = new TreeMap<>();
            sortedMap.putAll(request.getFileMap());

            Iterator<Map.Entry<String, MultipartFile>> itr = sortedMap.entrySet().iterator();

            while (itr.hasNext()) {
                Map.Entry<String, MultipartFile> fileEntry = itr.next();
                if (!fileEntry.getKey().contains(pattenName)) {
                    MultipartFile multipartFile = request.getFile(fileEntry.getKey());
                    FileUpload fileUpload = saveFile(uploadConstants, multipartFile, false);
                    if (fileUpload != null) {
                        result.add(fileUpload);
                    }
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 根据传入的文件名的key获取提交的文件
     *
     * @param httpServletRequest
     * @param uploadConstants
     * @return
     * @throws IOException
     */
    public List<FileUpload> filesUploadKeyInclude(final HttpServletRequest httpServletRequest, String uploadConstants, String fileKey) throws IOException {
        MultipartHttpServletRequest request = transferMultipartHttp(httpServletRequest);
        if (request != null) {
            List<FileUpload> result = new ArrayList<>();

            SortedMap<String, MultipartFile> sortedMap = new TreeMap<>();
            sortedMap.putAll(request.getFileMap());
            Iterator<Map.Entry<String, MultipartFile>> itr = sortedMap.entrySet().iterator();

            while (itr.hasNext()) {
                Map.Entry<String, MultipartFile> fileEntry = itr.next();

                if (ToolUtil.contains(fileEntry.getKey(), fileKey)) {
                    MultipartFile multipartFile = request.getFile(fileEntry.getKey());
                    FileUpload fileUpload = saveFile(uploadConstants, multipartFile, false);
                    if (fileUpload != null) {
                        result.add(fileUpload);
                    }
                }
            }
            return result;
        }
        return null;
    }

    /**
     * HttpServletRequest 转换为 MultipartHttpServletRequest
     *
     * @param httpServletRequest
     * @return
     */
    private MultipartHttpServletRequest transferMultipartHttp(final HttpServletRequest httpServletRequest) {
        MultipartHttpServletRequest request;
        try {
            request = (MultipartHttpServletRequest) httpServletRequest;
        } catch (Exception ex) {
            return null;
        }
        return request;
    }

    /**
     * 获取分页插件
     *
     * @return
     */
    protected BasicRowBounds getPageBounds() {
        int pageSize = 10, pageIndex = 1;
        if (StringUtils.isNotEmpty(getPara("pageIndex")) && StringUtils.isNumeric(getPara("pageIndex"))) {
            pageIndex = Integer.valueOf(getPara("pageIndex"));
        }
        if (StringUtils.isNotEmpty(getPara("pageSize")) && StringUtils.isNumeric(getPara("pageSize"))) {
            pageSize = Integer.valueOf(getPara("pageSize"));
        }
        log.info("使用分页插件：{pageIndex=" + pageIndex + ",pageSize=" + pageSize + "}");
        return new BasicRowBounds(pageIndex, pageSize);
    }

    /**
     * 获取分页参数
     *
     * @return
     * @throws RuntimeException
     */
    public Page getDefaultPage() throws RuntimeException {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String pageSize = httpServletRequest.getParameter("pageSize");
        String pageIndex = httpServletRequest.getParameter("pageIndex");
        Page page = new Page();
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        if (StringUtils.isEmpty(pageIndex)) {
            pageIndex = "1";
        }
        Integer size = null;
        Integer index = null;
        try {
            size = Integer.valueOf(pageSize);
            index = Integer.valueOf(pageIndex);
        } catch (Exception e) {
            throw new RuntimeException("传入的分页参数错误");
        }
        page.setSize(size);
        page.setCurrent(index);
        return page;
    }


    /**
     * 将音频文件转换为MP3格式
     *
     * @param fileUpload
     * @return
     */
    protected void transferMp3(FileUpload fileUpload) {
        if (fileUpload != null && StringUtils.isNotEmpty(fileUpload.getSave_path()) && !isMp3(fileUpload.getSave_path())) {
            String absolutelyPath = apiProperties.getFileUploadPath() + "/" + fileUpload.getSave_path();
            String transferPath = transferSuffixMp3(absolutelyPath);
            long bitRate = 0;
            try {
                MP3File mp3File = new MP3File(absolutelyPath);//封装好的类
                MP3AudioHeader header = mp3File.getMP3AudioHeader();
                System.out.println("时长: " + header.getTrackLength()); //获得时长
                bitRate = header.getBitRateAsNumber();
                System.out.println("比特率: " + header.getBitRateAsNumber()); //获得比特率
                System.out.println("音轨长度: " + header.getTrackLength()); //音轨长度
                System.out.println("格式: " + header.getFormat()); //格式，例 MPEG-1
                System.out.println("声道: " + header.getChannels()); //声道
                System.out.println("采样率: " + header.getSampleRate()); //采样率
                System.out.println("MPEG: " + header.getMpegLayer()); //MPEG
                System.out.println("MP3起始字节: " + header.getMp3StartByte()); //MP3起始字节
                System.out.println("精确的音轨长度: " + header.getPreciseTrackLength()); //精确的音轨长度

            } catch (IOException e) {
                e.printStackTrace();
            } catch (TagException e) {
                e.printStackTrace();
            } catch (ReadOnlyFileException e) {
                e.printStackTrace();
            } catch (InvalidAudioFrameException e) {
                e.printStackTrace();
            }
            execCmd("ffmpeg -i " + absolutelyPath + " -vn -c:a libmp3lame -aq 4 -ac 2 " + transferPath);
            fileUpload.setSave_path(transferSuffixMp3(fileUpload.getSave_path()));
        }
    }

    /**
     * 判断文件是否是mp3格式
     *
     * @param fileName
     * @return
     */
    private boolean isMp3(String fileName) {
        if ("mp3".toLowerCase().equals(fileName.substring(fileName.indexOf('.') + 1, fileName.length()).toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 将后缀转换为MP3
     *
     * @param filePath
     * @return
     */
    private String transferSuffixMp3(String filePath) {
        return filePath.substring(0, filePath.indexOf(".") + 1) + "mp3";
    }

    /**
     * 执行系统任务
     *
     * @param command
     */
    private void execCmd(String command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec(command);
                    BufferedInputStream bis = new BufferedInputStream(
                            process.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(bis));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }

                    process.waitFor();
                    if (process.exitValue() != 0) {
                        System.out.println("error!");
                    }
                    bis.close();
                    br.close();
                } catch (IOException e) {
                    log.error("execCmd:{}", e);
                } catch (InterruptedException e) {
                    log.error("execCmd:{}", e);
                }
            }
        }).start();
    }


    /**
     * H5上获取token值
     *
     * @return
     */
    protected String getWebToken() {
        String token = getPara("token");
        if (StringUtils.isEmpty(token)) {
            token = getCookieValue("token");
        }
        addCookie("token", token);
        return token;
    }


    /**
     * 获取icon图片 (1: 法律咨询 2: 反映问题  3: 困难帮扶-我的申请)
     *
     * @return
     */
    public String flwqConfig(int type) {
        String image = "";
        JSONObject json = (JSONObject) getAppConfig("images");
        if (type == 1) {
            JSONArray consult = json.getJSONArray("consult");
            JSONObject f = consult.getJSONObject(0);
            image = WebSiteUtil.getBrowseUrl(f.getString("flzximage"));
        } else if (type == 2) {
            JSONArray consult = json.getJSONArray("question");
            JSONObject f = consult.getJSONObject(0);
            image = WebSiteUtil.getBrowseUrl(f.getString("fywtimage"));
        } else if (type == 3) {
            JSONArray consult = json.getJSONArray("diffhelp");
            JSONObject f = consult.getJSONObject(0);
            image = WebSiteUtil.getBrowseUrl(f.getString("knbfimage"));
        }

        return image;
    }

    /**
     * 返回法律援助的图片icon
     *
     * @param type
     * @return
     */
    public String getLawSupportImgage(Integer type) {
        JSONObject json = (JSONObject) getAppConfig("images");
        return json.getJSONObject("lawSupport").getString("type_" + type);
    }


    /**
     * 移除map中的键值对
     */
    public Map removeValue(Map map, String key) {
        if (map != null && map.containsKey(key)) {
            map.remove(key);
        }
        return map;
    }

    public List<Map<String, Object>> removeValue(List<Map<String, Object>> list, String key) {
        if (list == null || list.size() == 0) return list;
        for (Map m : list) {
            removeValue(m, key);
        }
        return list;
    }

    public List<Map<String, Object>> removeValue(List<Map<String, Object>> list, String[] keys) {
        if (keys == null || keys.length == 0) {
            return list;
        }
        for (Map<String, Object> m : list) {
            Iterator<Map.Entry<String, Object>> iterator = m.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                for (String key : keys) {
                    if (key.equals(next.getKey())) {
                        iterator.remove();
                    }
                }

            }
        }
        return list;
    }

}
