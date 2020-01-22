package com.gobestsoft.common.modular.dept.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.DeptMember;
import com.gobestsoft.common.modular.dept.model.DeptMemberInfoEntity;
import com.gobestsoft.common.modular.dept.model.MemberCountModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 会员信息
 *
 * @author xss
 * @date 2018-08-15
 */
@Repository
public interface DeptMemberMapper extends BaseMapper<DeptMember> {

    /**
     * 多条件分页查询方法
     *
     * @param page
     * @return
     */
    public List<Map<String, Object>> selectByCondition(
            @Param("page") Page<Map<String, Object>> page,
            Map<String,Object> map);

    /**
     * 查询需要删除的会员列表
     * @param page
     * @param map
     * @return
     */
    public List<Map<String, Object>> selectByCondition4Remove(
            @Param("page") Page<Map<String, Object>> page,
            Map<String,Object> map);

    /**
     * 查询所有会员信息的列，字典值转中文名称
     * @return
     */
    public List<DeptMemberInfoEntity> getMemberList(
            Map<String,Object> map);

    /**
     * 查询当前登录用户所属组织下及子组织、子部门下的会员
     *
     * @param page
     * @param name
     * @param unionName
     * @param deptId
     * @return
     */
    public List<Map<String, Object>> selectByConditionByDeptId(
            @Param("page") Page<Map<String, Object>> page,
            @Param("name") String name,
            @Param("deptNo") String deptNo,
            @Param("memberCard") String memberCard,
            @Param("unionName") String unionName,
            @Param("deptId") Integer deptId);

    /**
     * 根据会员id查询会员信息
     *
     * @return
     */
    public List<Map<String, Object>> selectByMemberId(@Param("id") Integer id);

    /**
     * 根据所属工会id,获取会员id拼接的字符串,多个id以逗号分隔
     *
     * @param deptId
     * @return
     */
    public String getMemberIdsByUnionId(@Param("deptId") Integer deptId);

    /**
     * 创建临时表
     *
     * @param tableName
     */
    public void createTemporaryTable(@Param("tableName") String tableName);

    /**
     * 删除临时表
     *
     * @param tableName
     */
    public void dropTemporaryTable(@Param("tableName") String tableName);

    /**
     * 创建存储过程
     *
     * @param tableName
     */
    public void createImportMemberProcedure(@Param("procedureName") String procedureName, @Param("tableName") String tableName, @Param("deptId") Integer deptId, @Param("createUid") String createUid, @Param("createTime") String createTime);

    /**
     * 调用存储过程
     *
     * @param procedureName
     * @return
     */
    public List<Map<String, Object>> callImportMemberProcedure(@Param("procedureName") String procedureName);

    /**
     * 删除存储过程
     *
     */
    public void dropImportMemberProcedure(@Param("procedureName") String procedureName);

    /**
     * 批量更新会员的type字段值
     *
     * @param deptMembers
     * @param type
     */
    void updateTypeByList(@Param("deptMembers") List<Map<String, Object>> deptMembers, @Param("type") String type);

    /**
     * 插入所有
     *
     * @param pojos
     */
    void insertList(@Param("tableName") String tableName, @Param("pojos") List<Map<String, String>> pojos);


    /**
     * 获取会员信息
     *
     * @param memberId
     * @return
     */
    DeptMemberInfoEntity getMemberInfo(@Param("memberId") Integer memberId);

    /**
     * 会员信息 主表是会员表person_info
     * @param person_id
     * @return
     */
    DeptMemberInfoEntity getMemberInfo4Remove(@Param("memberId") Integer person_id);

    Map<String,Object> selectOrganizationByDeptId(@Param("dept_id") Integer dept_id);


    void clearAppuser(@Param("member_id")Integer member_id);

    public List<MemberCountModel> selectMemberCountList(@Param(("deptId")) Integer deptId);


    /**
     * 查询卡号为空的所有会员 前1000
      * @return
     */
    List<DeptMemberInfoEntity> selectMemberStationCardNull();

    /**
     * 更新会员卡号为空的卡号
     * @param id
     * @param station_card
     */
    void updateStationCard(@Param("id") String id,@Param("station_card") String station_card);

    /**
     * 查询是否是农业/非农户口
     * @param page
     * @param map
     * @return
     */
    public List<Map<String, Object>> selectByHouseHold(
            @Param("page") Page<Map<String, Object>> page,
            Map<String,Object> map);


    /**
     * 查询返乡活动
     * @param page
     * @param map
     * @return
     */
    public List<Map<String, Object>> selectByRehome(
            @Param("page") Page<Map<String, Object>> page,
            Map<String,Object> map);

    /**
     * 调用返乡存储过程
     */
    public int getReHomeTicket();

    /**
     * 后台转会审核
     */
    public List<Map<String, Object>> selectByMemrehomeCheck(
            @Param("page") Page<Map<String, Object>> page,
            Map<String,Object> map);
}
