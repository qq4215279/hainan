package com.gobestsoft.common.modular.system.dao;

import com.gobestsoft.common.constant.model.OrganizationRankData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlackboardDao {

    /**
     * 工会会员数量
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    Integer getMemberCountByDeptId(@Param("deptId") Integer deptId);

    /**
     * 工会会员卡数量
     *
     * @param deptId
     * @return
     */
    Integer getMemberCardCountByDeptId(@Param("deptId") Integer deptId);

    /**
     * 获取下级工会数量
     *
     * @param deptId
     * @return
     */
    Integer getSubordinateDeptCount(@Param("deptId") Integer deptId);

    /**
     * 会员人数（含下级工会）
     *
     * @return
     * @date 2018年10月09日 上午10:20:34
     */
    Integer getMemberCountAndSubordinateByDeptId(@Param("deptId") Integer deptId);


    /**
     * 统计会员人数
     *
     * @return
     */
    Map<String, Object> memberStatistics();

    /**
     * 统计注册会员人数
     *
     * @return
     */
    Map<String, Object> registerMember();

    /**
     * 近一年各月注册和入会人员统计
     *
     * @param months
     * @return
     */
    List<Map<String, Object>> lastYearMonthStatistics(@Param("months") List<Map<String, String>> months);

    /**
     * 每个月新增组织数排行
     *
     * @return
     */
    List<OrganizationRankData> organizationalRank(@Param("months") List<Map<String, String>> months);

    /**
     * 每个月组织数排行
     *
     * @return
     */
    List<OrganizationRankData> organizationalRank2(@Param("months") List<Map<String, String>> months);

    /**
     * 获取组织数
     *
     * @param pid
     * @return
     */
    Integer organizationalRanks(@Param("pid") Integer pid);

    /**
     * 会员前十
     *
     * @return
     */
    List<Map<String, Object>> memberTop10Statistics();

    /**
     * 省总工会直属的id
     *
     * @return
     */
    List<OrganizationRankData> provincialUnionsTop10();

    /**
     * 省总工会直属的会员排名前十
     *
     * @param parentId
     * @return
     */
    List<Map<String, Object>> selectByMember(List<Integer> parentId);

    /**
     * 省总工会直属的会员排名更多
     *
     * @param parentId
     * @return
     */
    List<Map<String, Object>> selectByMemberMore(List<Integer> parentId);

    /**
     * 每个月组织数排行
     *
     * @return
     */
    Map<String, Object> memberRank(@Param("month") Map<String, String> months);


    /**
     * 基层企业工会前十统计
     *
     * @return
     */
    List<Map<String, Object>> grassrootsEnterprisesTop10();

    /**
     * 近一年入会人数排名前五工会统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> joinOrganizationTop5(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 分组查询民族人数
     *
     * @return
     */
    List<Map<String, Object>> getMemberInfoByNation();

    /**
     * 分组查询学历人数
     *
     * @return
     */
    List<Map<String, Object>> getMemberInfoByEducation();

    /**
     * 分组查询农民工
     *
     * @return
     */
    List<Map<String, Object>> getMemberInfoByFarmer();

    /**
     * 按年龄段查询人数
     *
     * @return
     */
    Map<String, Object> getMemberInfoByAge(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 分组查询户籍人数
     *
     * @return
     */
    List<Map<String, Object>> getMemberInfoByDocile();

    /**
     * 分组查询性别
     *
     * @return
     */
    List<Map<String, Object>> getMemberCountBySex();

    /**
     * 所有会员人数
     *
     * @return
     */
    Integer selectMemberCount();

    /**
     * 根据指定日期查询户口类型
     *
     * @param date      插入日期
     * @param household 户口类型
     * @return
     */
    List<Map<String, Object>> selectHouseHoldMemberByDate(@Param("date") String date, @Param("household") String household);

    /**
     * 各级工会返乡统计
     *
     * @return
     */
    List<Map<String, Object>> szghRehomeUnionsTop10();

    /**
     * 省总工会直属工会返乡统计
     */
    List<Map<String, Object>> szgzUnderUnionsTop10();

    /**
     * 省总下级具体工会返乡返乡统计
     */
    List<Map<String, Object>> szghAllRehomeStat(@Param("deptId") String deptId);


    /**
     * 返乡总人数
     */
    Integer getTotalCnt();

    /**
     * 返乡总人数
     */
    Integer getAllTotalCnt(@Param("deptId") String deptId);

    /**
     * 各级工会返乡工会数
     */
    Integer getUnionbyCnt(@Param("deptId") String deptId);

    /**
     * 工会总会员数
     */
    Integer getAllMemCnt(@Param("deptId") Integer deptId);

    /**
     * 工会绑定会员人数
     */
    Integer getAllAuthMemCnt(@Param("deptId") Integer deptId);

    /**
     * 工会农民工人数
     */
    Integer getAllFarmerCnt(@Param("deptId") Integer deptId);

    /**
     * t_member_count 和 sys_dept 关联的所有deptId
     */
    List<Integer> getAllDeptId();

    /**
     * 更新 t_member_count 两个会员人数字段
     */
    void adjustMemberCount(@Param("deptId") Integer deptId);

    /**
     * 查询 各市县产业系统工会实名制数据
     */
    List<Integer> getRealNameData(@Param("deptId") Integer deptId);

    List<Map<String,Object>> getCacheRealData();

    /**
     * 插入 各市县产业系统工会实名制数据--统计表
     */
    void insertRealNameData(List<Map<String,Object>> data);

    int getRealDataCount(Integer orgId);


    void updateRealData(Map<String, Object> obj);
}
