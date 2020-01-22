package com.gobestsoft.mamage.moudle.system.service;

import com.gobestsoft.common.modular.dao.mapper.DeptMemApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 仅仅用于操作没有关联公会组织的会员申请
 */
@Service
public class DeptMemApplySerivce {

    @Autowired
    DeptMemApplyMapper deptMemApplyMapper;

    public void updateMemberDept(String deptId,String id){

        List<String> list = deptMemApplyMapper.selectSameUnitById(id);

        deptMemApplyMapper.updateMemberDept(deptId,list);

    }


}
