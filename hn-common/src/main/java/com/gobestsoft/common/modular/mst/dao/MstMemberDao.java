package com.gobestsoft.common.modular.mst.dao;

import java.util.List;
import java.util.Map;

import com.gobestsoft.common.modular.dept.model.DeptMember;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.mst.model.MstMember;
import org.springframework.stereotype.Repository;

/**
 * 会员信息Dao层接口
 *
 * @author zhangdw
 * 2018-05-11 下午4点 10分
 */
@Repository
public interface MstMemberDao extends MstBaseDao<MstMember> {

    /**
     * 多条件分页查询方法
     *
     * @param page
     * @param name      姓名
     * @param unionName 所属工会组织名称
     * @param unionId   所属工会组织id
     * @return
     */
    public List<MstMember> selectListPage(
            @Param("page") Page<MstMember> page,
            @Param("name") String name,
            @Param("unionName") String unionName,
            @Param("unionId") Integer unionId);
    
    /**
     * 查询当前登录用户所属组织下及子组织、子部门下的会员
     * @param page
     * @param name
     * @param unionName
     * @param orgId
     * @return
     */
    public List<MstMember> selectListPageByOrgId(
            @Param("page") Page<MstMember> page,
            @Param("name") String name,
            @Param("unionName") String unionName,
            @Param("orgId") Integer orgId);

	/**
	 * 根据工会组织id,查询会员的男的人数
	 * @param unionId
	 * @param sex
	 * @return
	 */
	public Integer selectMemberMaleCountByUnionid(
            @Param("unionId") Integer unionId,
            @Param("sex") Integer sex);
	
    /**
     * 查看身份证是否存在
	 * 根据会员身份证号、姓名查询出会员信息
     * @param idcardMumber 身份证号
     * @param name
     * @return
     */
	public List<DeptMember> selectByIdcardNumber(
            @Param("idcardNumber") String idcardNumber,
            @Param("name") String name);

	/**
	 * 查看身份证是否存在
	 * @param idcardNumber
     * @return
     */
	public List<DeptMember> selectByNumber(
			@Param("idcardNumber") String idcardNumber);


	/**
	 * 根据工会id,获取父级工会下的会员和该工会下的会员信息
	 * 用于前台inputSelect选择框
	 * @param page
	 * @param unionId
	 * @param pid
	 * @param name
	 * @param unionName
	 * @param duty
	 * @return
	 */
	public List<Map<String, Object>> getPidMemberListByUnionId(@Param("page") Page<Map<String, Object>> page
            , @Param("unionId") Integer unionId
            , @Param("pid") Integer pid
            , @Param("name") String name
            , @Param("unionName") String unionName
            , @Param("duty") String duty);

	/**
	 * 根据所属工会id,获取会员id拼接的字符串,多个id以逗号分隔
	 * @param deptId
	 * @return
	 */
	public String getMemberIdsByUnionId(@Param("deptId") Integer deptId);
	
	/**
	 * 根据工会组织id,查询会员数量
	 * @param unionId
	 * @return
	 */
	public Integer selectCountByUnionId(@Param("unionId") Integer unionId);
}
