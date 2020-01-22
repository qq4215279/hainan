package com.gobestsoft.common.modular.dept.mapper;

import com.gobestsoft.common.constant.model.NewMediaData;
import com.gobestsoft.common.constant.model.OrganizationData;
import com.gobestsoft.common.constant.model.SeekLegalAdviceData;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public interface BigDataMapper {
    /**
     * 查询困难职工总数
     *
     * @param start_time 开始时间，非必填
     * @param end_time   结束时间，非必填
     * @return
     */
    Integer selectStraitenedCount(@Param("start_time") String start_time, @Param("end_time") String end_time, @Param("type") Integer type);

    NewMediaData selectUnionMediaData();

    /**
     * 建会信息
     *
     * @param bigDataAdditionTime
     * @return
     */
    OrganizationData selectOrgBuildData(@Param("bigDataAdditionTime") String bigDataAdditionTime);

    SeekLegalAdviceData selectLawAdviceData();

    /**
     * 注册用户
     *
     * @return
     */
    Integer selectAppUser();

    /**
     * 会员人数前10
     *
     * @param deptId
     * @return
     */
    List<Map<String, Object>> memberTop10Statistics(@Param("deptId") Integer deptId);

    /**
     * 每月会员人数
     *
     * @param deptId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> monthMemberStatistics(@Param("deptId") Integer deptId, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("type") Integer type);

    /**
     * 查询农民工和职工比例
     *
     * @param deptId
     * @return
     */
    List<Map<String, Object>> getMemberInfoByFarmer(@Param("deptId") Integer deptId);

    /**
     * 查询学历比例
     *
     * @param deptId
     * @return
     */
    List<Map<String, Object>> getMemberInfoByEducation(@Param("deptId") Integer deptId);

    /**
     * 查询年龄比例
     *
     * @param deptId
     * @return
     */
    Map<String, Object> getMemberInfoByAge(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("deptId") Integer deptId);

    /**
     * 按民族统计
     *
     * @param deptId
     * @return
     */
    List<Map<String, Object>> getMemberInfoByNation(@Param("deptId") Integer deptId);

    /**
     * 按性别统计
     *
     * @param deptId
     * @return
     */
    List<Map<String, Object>> getMemberCountBySex(@Param("deptId") Integer deptId);

    /**
     * 按人数
     *
     * @param deptId
     * @return
     */
    Integer selectMemberCount(@Param("deptId") Integer deptId);

    /**
     * 组织数
     *
     * @param deptId
     * @return
     */
    Integer selectDeptCount(@Param("deptId") Integer deptId);

    /**
     * 下级工会管理的所有组织数量
     *
     * @param deptId
     * @return
     */
//    List<Map<String, Object>> selectDeptCountGroup(@Param("deptId") Integer deptId);

    /**
     * 获取组织下会员户籍所在地
     *
     * @param deptId
     * @param startTime
     * @param entTime
     * @return
     */
    List<Map<String, Object>> getMemberInfoByDocile(@Param("deptId") Integer deptId,
                                                    @Param("startTime") String startTime,
                                                    @Param("entTime") String entTime);

    Integer getGspShopJoinActivityNum();
    Double getGspShopTotalAllFree();
    Integer getGspShopAllOrderNum();
    Integer getGspShopAllItemNum();
    LinkedList<String> getGspShopAllOrgIds();
    LinkedList<String> getGspShopIngActives(@Param("now") Long now);
    LinkedList<String> getGspShopEndActives(@Param("now") Long now);
    LinkedList<Map<String, Object>> getGspShopJoinMembers();
}
