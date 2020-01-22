package com.gobestsoft.admin.modular.contract.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.contract.dao.CollectiveContractMasterDao;
import com.gobestsoft.common.modular.contract.model.CollectiveContractMaster;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.model.LoginUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 集体合同
 */
@Service
public class ContractMasterService {

    @Resource
    private CollectiveContractMasterDao masterDao;


    /**
     * 列表
     */
    public List<Map<String, Object>> selectByCondition(Page<Map<String, Object>> page,String name) {
        List<Map<String, Object>> result = masterDao.selectByCondition(page, name);
        result.forEach(c -> {
            if (c.containsKey("filePath") && c.get("filePath") != null) {
                c.put("filePath", WebSiteUtil.getBrowseUrl(c.get("filePath").toString()));
            }
        });
        return result;
    }

    /**
     * 详情
     */
    public Object getDetailById(Integer id) {
        return masterDao.selectById(id);
    }

    /**
     * 保存
     */
    public void save(CollectiveContractMaster pojo, LoginUser user){
        String now = DateUtil.getAllTime();
        if(ToolUtil.isEmpty(pojo.getId())){
            pojo.setCreateUser(user.getName());
            pojo.setUnionId(user.getDeptId());
            pojo.setCode(this.getMasterCode());
            pojo.setCreateTime(now);
            masterDao.insert(pojo);
        }else{
            pojo.setUpdateUser(user.getName());
            pojo.setUpdateTime(now);
            masterDao.updateById(pojo);
        }
    }

    /**
     * 生成合同code
     */
    private String getMasterCode() {
        return DateUtil.getDays() + UUIDUtil.getCharAndNumr(6);
    }

    /**
     * 删除
     */
    public void delete(Integer id) {
        CollectiveContractMaster pojo = new CollectiveContractMaster();
        pojo.setId(id);
        pojo.setDelFlg(1);
        masterDao.updateById(pojo);
    }

}
