package com.gobestsoft.common.modular.approve.dao;

import java.util.List;
import java.util.Map;

import com.gobestsoft.common.modular.dao.model.OrgMemberPojo;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 会员申请的数据查询
 *
 * @author caoy
 * @date 2017-12-05
 */
public interface ApprMemberDao {

    /**
     * 条件查询（状态，关键字，开始日期，结束日期）分页
     *
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectByCondition(@Param("page") Page<Map<String, Object>> page,
                                                @Param("status") String status,
                                                @Param("keyword") String keyword,
                                                @Param("begDate") String begDate,
                                                @Param("endDate") String endDate,
                                                @Param("applyType") String applyType,
                                                @Param("orgId") Integer orgId);

    /**
     * 详细信息查询
     *
     * @return List<Map<String, Object>>
     */
    Map<String, Object> getDetails(@Param("id") String id);

    /**
     * 插入记录并返回ID值
     *
     * @param orgMember
     * @return
     */
    Long insertAndGetId(@Param("orgMember") OrgMemberPojo orgMember);

    /**
     * 更新app用户 会员ID
     * @param auid
     * @param ouid
     */
    void updateOuidByAuId(@Param("auid") String auid, @Param("ouid") String ouid);
}
