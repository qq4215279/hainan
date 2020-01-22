package com.gobestsoft.mamage.moudle.dept.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.mapper.SysImportLogMapper;
import com.gobestsoft.common.modular.dao.model.SysImportLogPojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.moudle.dept.model.ImportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by gutongwei
 * on 2018/9/4 上午9:48
 */
@Service
public class DeptOtherService {

    @Autowired
    private SysImportLogMapper importLogMapper;

    /**
     * 导入日志
     *
     * @param uid  用户ID
     * @param type 导入类型0：导入组织；1：导入会员。
     * @param page
     * @return
     */
    public List<Map<String, String>> importLog(String uid, int type, Page page) {
        List<Map<String, String>> result = new ArrayList<>();
        List<SysImportLogPojo> logs = importLogMapper.selectPage(page, new EntityWrapper().eq("create_uid", uid).eq("type", type).orderBy("create_time", false));
        logs.forEach(l -> {
            Map<String, String> map = new HashMap<>();
            map.put("downLink", WebSiteUtil.getBrowseUrl(l.getDownLink()));
            map.put("createTime", DateUtil.parseAndFormat(l.getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
            map.put("remark", l.getRemark());
            if (l.getStatus() == 0) {
                map.put("statusDesc", "导入中");
            } else if (l.getStatus() == 1) {
                map.put("statusDesc", "完成");
            } else if (l.getStatus() == 2) {
                map.put("statusDesc", "导入失败");
            }
            map.put("status", l.getStatus().toString());
            result.add(map);
        });
        return result;
    }

    /**
     * 插入导入日志
     *
     * @param uid  用户ID
     * @param type 导入类型0：导入组织；1：导入会员。
     * @return
     */
    public Integer addImportLog(String uid, int type) {
        SysImportLogPojo pojo = new SysImportLogPojo();
        pojo.setCreateTime(DateUtil.getAllTime());
        pojo.setCreateUid(uid);
        pojo.setStatus(0);
        pojo.setType(type);
        importLogMapper.insert(pojo);
        return pojo.getId();
    }


    /**
     * 更新状态
     *
     * @param id
     * @param status
     */
    public void setImportStatus(int id, int status, String downLink, String remark) {
        SysImportLogPojo pojo = importLogMapper.selectById(id);
        if (pojo == null) return;
        pojo.setStatus(status);
        pojo.setDownLink(downLink);
        pojo.setRemark(remark);
        pojo.updateById();
    }

    /**
     * 上传的文件进行逻辑保存
     */
    public void uploadSaveFile(ImportModel importModel, String fileUploadPath, DeptOtherService deptOtherService, Integer logId) throws IOException, FileNotFoundException {
        if (!importModel.isHasError()) {
            //输出Excel文件
            String fileName = UploadConstants.EXPORT_EXCEL + DateUtil.getDays() + "/" + new String(UUIDUtil.getUUID().getBytes("utf-8"), "utf-8") + ".xls";
            String pathName = fileUploadPath + fileName;
            System.out.printf("保存路径：" + pathName);
            File file = new File(pathName);
            file.getParentFile().mkdirs();

            FileOutputStream output = new FileOutputStream(pathName);
            importModel.getWorkbook().write(output);
            output.flush();
            output.close();
            deptOtherService.setImportStatus(logId, 1, WebSiteUtil.getBrowseUrl(fileName), importModel.getRemark());
        } else {
            deptOtherService.setImportStatus(logId, 2, "", importModel.getRemark());
        }

    }

}
