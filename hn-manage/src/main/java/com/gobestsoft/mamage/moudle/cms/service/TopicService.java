package com.gobestsoft.mamage.moudle.cms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.mapper.CmsCategoryMapper;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.FileUtil;
import com.gobestsoft.core.util.StringRegularUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by duanmu on 2018/9/11.
 */
@Service
public class TopicService {

    @Resource
    private CmsCategoryMapper cmsCategoryMapper;

    @Resource
    private ManageProperties manageProperties;

    /**
     * 条件查询 type 0:专题 1:栏目
     */
    public List<CmsCategoryPojo> getTopicList(Page<CmsCategoryPojo> page,
                                              String name,
                                              String begDate,
                                              String endDate) {
        List<CmsCategoryPojo> resultList = cmsCategoryMapper.selectTopicList(page, name, begDate, endDate,0);
        return resultList;
    }

    /**
     * 专题增加
     */
    @Transactional
    public Integer addTopic(CmsCategoryPojo pojo, String imgStr, String uid) throws IOException{
        pojo.setPid(1000);
        pojo.setName(pojo.getName());
        //设置delFlg为没有删除
        pojo.setDelFlg(0);
        // 设置创建时间
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYYMMddHHmmss");
        Date date = new Date();
        String createTime = sdf2.format(date);
        pojo.setCreateTime(createTime);
        pojo.setUpdateTime(createTime);
        pojo.setCreateUser(uid);
        pojo.setUpdateUser(uid);
        // 传入图片 并返回图片相对路径,插入CmsCategoryPojo对象中
        String relativePath = manageProperties.getFileUploadPath();
        String folderName = UploadConstants.ICON + DateUtil.getDays();
        String coverPath = this.getTopicCoverStr(imgStr, relativePath, folderName);
        pojo.setIcon(coverPath);

        // 将封装好的对象传入insert方法
        int result = cmsCategoryMapper.insert(pojo);
        return result;
    }

    /**
     * 获取编辑数据
     */
    public CmsCategoryPojo getTopicInfoById(String id){

        CmsCategoryPojo pojo = cmsCategoryMapper.getTopicInfoById(id);
        String coverPath = pojo.getIcon();
        coverPath = WebSiteUtil.getBrowseUrl(coverPath);
        pojo.setIcon(coverPath);

        return pojo;
    }

    /**
     * 专题编辑处理
     */
    @Transactional
    public Integer updateTopicById(CmsCategoryPojo pojo, String imgStr) throws IOException{
        // 当前时间取得
        String nowTime = DateUtil.getAllTime();

        CmsCategoryPojo pojo1 = cmsCategoryMapper.getTopicInfoById(pojo.getId().toString());
        pojo1.setUpdateUser(pojo.getUpdateUser());
        pojo1.setUpdateTime(nowTime);
        pojo1.setName(pojo.getName());

        // 获取上传路径 + 目录
        String relativePath = manageProperties.getFileUploadPath();
        String folderName = UploadConstants.ICON + DateUtil.getDays();
        String coverPath = this.getTopicCoverStr(imgStr, relativePath, folderName);

        pojo1.setIcon(coverPath);

        int result = cmsCategoryMapper.updateTopicInfoById(pojo1);
        if (result != 1) {
            throw new BusinessException(BizExceptionEnum.SERVER_ERROR);
        }

        return result;

    }

    /**
     * 删除专题资讯
     */
    @Transactional
    public void delById(String id){
        cmsCategoryMapper.delTopic(id);
    }


    /**
     * base64
     *
     * @param
     * @return 当图路径
     * @throws IOException
     */
    private String getTopicCoverStr(String str, String relativePath, String folderName) throws IOException {
        if (StringRegularUtil.isURL(str)) {
            return str;
        }
        String img = str.replace("data:image/jpeg;base64,", "");
        String coverPath = FileUtil.createCover(img, relativePath, folderName);
        return coverPath;
    }


}
