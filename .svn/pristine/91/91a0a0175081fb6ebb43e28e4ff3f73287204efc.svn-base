package com.gobestsoft.mamage.moudle.law.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.law.mapper.LawCommonProblemMapper;
import com.gobestsoft.common.modular.law.model.LawCommonProblem;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.mamage.model.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * create
 * on 2018/11/1 下午1:17
 */
@Service

public class LawCommonProblemService {

    @Resource
    LawCommonProblemMapper lawCommonProblemMapper;

    /**
     * 查询问题名称信息
     * @param page
     * @return
     */
    public List<Map<String, Object>> selectLawCommonProblem(Page<Map<String, Object>> page){
        return lawCommonProblemMapper.selectLawCommonProblem(page);
    }

    /**
     * 插入方法
     *
     * @param deptLegalApply
     * @param user
     */
    @Transactional
    public void insertByLawCommonProblem(LawCommonProblem lawCommonProblem) {

        lawCommonProblem.setCreateTime(DateUtil.getAllTime());
        lawCommonProblemMapper.insert(lawCommonProblem);
//        if (StringUtils.isNotEmpty(lawCommonProblem.getStatus())) {
//            //插入审核日志
//            insertLogByDeptLegalApply(lawCommonProblem, user);
//        }
    }

    /**
     * 更新方法
     *
     * @param deptLegalApply
     * @param user
     */
    public void updateByLawCommonProblem(LawCommonProblem lawCommonProblem) {
        lawCommonProblemMapper.updateById(lawCommonProblem);
//        if (StringUtils.isNotEmpty(lawCommonProblem.getStatus())) {
//            //插入审核日志
//            insertLogByDeptLegalApply(lawCommonProblem, user);
//        }
    }

    /**
     * 查询方法
     *
     * @param id
     */
    public LawCommonProblem selectById(Integer id)
    {
        return lawCommonProblemMapper.selectById(id);
    }

    /**
     * 删除方法
     *
     * @param id
     */
    public void remove(Integer id) {
        lawCommonProblemMapper.deleteById(id);
    }

}
