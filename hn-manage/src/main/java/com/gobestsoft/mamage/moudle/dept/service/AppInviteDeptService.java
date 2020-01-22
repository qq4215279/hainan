package com.gobestsoft.mamage.moudle.dept.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictGroupCodeConstants;
import com.gobestsoft.common.modular.dept.mapper.DeptInviteMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberApplyMapper;
import com.gobestsoft.common.modular.dept.mapper.PersonInfoMapper;
import com.gobestsoft.common.modular.dept.model.DeptMemberApply;
import com.gobestsoft.common.modular.dept.model.PersonInfo;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.model.Dict;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 入会邀请
 *
 * @author xiat
 * @time 2018-11-14 16:25:01
 */
@Service
@Slf4j
public class AppInviteDeptService {

    @Resource
    private DeptInviteMapper deptInviteMapper;

    @Resource
    PersonInfoMapper personInfoMapper;

    @Resource
    DeptMemberApplyMapper deptMemberApplyMapper;

    @Resource
    DictMapper dictMapper;

    public List<Map<String, Object>> getSelectAppInviteDateList(Page<Map<String, Object>> page,
                                                                String unionName,
                                                                String begDate,
                                                                String endDate,
                                                                Integer deptId,
                                                                String userId) {
        return deptInviteMapper.getSelectAppInviteDateList(page, unionName, begDate, endDate, deptId, userId);
    }

    /**
     * 查询各单位的人员详细
     *
     * @param page
     * @param unitName
     * @param name
     * @param memberCard
     * @param deptId
     * @param memberRange
     * @param map
     */
    public List<Map<String, Object>> getAppInviteDeptDataToModel(Page<Map<String, Object>> page, String unitName, String name, String memberCard, Integer deptId, String memberRange, Map<String, Object> map) {
        map.put("unitName",unitName);
        map.put("name", name);
        map.put("memberCard", memberCard);
        map.put("deptId", deptId);
        map.put("memberRange", memberRange);
        return deptInviteMapper.getAppInviteDeptDataToModel(page, map);
    }


}
