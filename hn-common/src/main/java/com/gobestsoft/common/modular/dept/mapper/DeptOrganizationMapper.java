package com.gobestsoft.common.modular.dept.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 组织信息
 *
 * @author xss
 * @date 2018-08-15
 */
@Repository
public interface DeptOrganizationMapper extends BaseMapper<DeptOrganization> {

    /**
     * 根据条件查询工会信息管理列表
     *
     * @param page
     * @param unionName 工会名称
     * @param deptNo    工会编号
     * @param pId       上级工会id（sys_dept表id）
     * @return
     */
    List<Map<String, Object>> selectByCondition(
            @Param("page") Page<Map<String, Object>> page,
            @Param("unionName") String unionName,
            @Param("deptNo") String deptNo,
            @Param("pId") Integer pId);


    /**
     * 申请建会审核多条件分页查询
     *
     * @param page
     * @param status
     * @param unitName
     * @param orgId
     * @param perfectInformations 是否完善信息
     * @return
     */
    List<DeptOrganization> selectAuditOrganizationPage(@Param("page") Page<DeptOrganization> page
            , @Param("status") Integer status
            , @Param("unitName") String unitName
            , @Param("orgId") Integer orgId
            , @Param("perfectInformations") String[] perfectInformations);

    /**
     * 创建临时表
     *
     * @param tableName
     */
    void createTemporaryTable(@Param("tableName") String tableName);

    /**
     * 删除临时表
     *
     * @param tableName
     */
    void dropTemporaryTable(@Param("tableName") String tableName);

    /**
     * 创建存储过程
     *
     * @param tableName
     */
    void createImportDeptProcedure(@Param("procedureName") String procedureName, @Param("tableName") String tableName, @Param("createUid") String createUid, @Param("createTime") String createTime);

    /**
     * 调用存储过程
     *
     * @param procedureName
     */
    List<Map<String, Object>> callImportDeptProcedure(@Param("procedureName") String procedureName);

    /**
     * 删除存储过程
     *
     * @param procedureName
     */
    void dropImportDeptProcedure(@Param("procedureName") String procedureName);

    /**
     * 插入所有
     *
     * @param pojos
     */
    void insertList(@Param("tableName") String tableName, @Param("pojos") List<Map<String, String>> pojos);

    /**
     * 根据组织查取公司信息
     *
     * @param deptId
     * @return
     */
    DeptOrganization selectByDeptId(@Param("deptId") Integer deptId);

    /**
     * 查询所有组织信息，用于导出
     * @param unionName
     * @param deptNo
     * @param pId
     * @return
     */
    List<Map<String,Object>> selectAllByCondition(
                                              @Param("unionName") String unionName,
                                              @Param("deptNo") String deptNo,
                                              @Param("pId") Integer pId);

    /**
     * 查询需要删除的账号列表
     * @param page
     * @param map
     * @return
     */
    List<Map<String, Object>> selectByRegRemove(
            @Param("page") Page<Map<String, Object>> page,
            Map<String,Object> map);

    /**
     * 查询需要删除的重复组织
     * @param page
     * @param map
     * @return
     */
    List<Map<String, Object>> selectByOrgRemove(
            @Param("page") Page<Map<String, Object>> page,
            Map<String,Object> map);

    void updateEmailByUserName(@Param("unionName") String username, @Param("unionEmail") String email);

    /**
     * 根据deptId查询会员人数
     */
    Integer selectMemberCount(@Param(("deptId")) Long deptId);

}
