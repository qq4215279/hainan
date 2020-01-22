package com.gobestsoft.mamage.moudle.cms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.mapper.CmsCategoryMapper;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 栏目管理
 */
@Service
public class ColumnService {

    @Resource
    private CmsCategoryMapper cmsCategoryMapper;

    /**
     * 条件查询 type 0:专题 1:栏目
     */
    public List<CmsCategoryPojo> selectByCondition(Page<CmsCategoryPojo> page,
                                              String name,
                                              String begDate,
                                              String endDate) {
        List<CmsCategoryPojo> resultList = cmsCategoryMapper.selectTopicList(page, name, begDate, endDate,1);
        return resultList;
    }

    /**
     * 保存
     */
    public void save(CmsCategoryPojo pojo, String uid){
        String now = DateUtil.getAllTime();
        pojo.setUpdateTime(now);
        pojo.setUpdateUser(uid);

        if(ToolUtil.isNotEmpty(pojo.getId())){
            cmsCategoryMapper.updateById(pojo);
        }else{
            int max = cmsCategoryMapper.getMaxIdByPid(100);
            //栏目的id需要小于1000
            pojo.setId(max + 1);
            pojo.setPid(100);
            pojo.setName(pojo.getName());
            pojo.setDelFlg(0);
            pojo.setCreateTime(now);
            pojo.setCreateUser(uid);
            cmsCategoryMapper.insertColumnAll(pojo);
        }
    }

    /**
     * 获取详情
     */
    public CmsCategoryPojo getDetailById(Integer id){
        return cmsCategoryMapper.selectById(id);
    }

    /**
     * 删除
     */
    public void delete(String id){
        cmsCategoryMapper.delTopic(id);
    }

}
