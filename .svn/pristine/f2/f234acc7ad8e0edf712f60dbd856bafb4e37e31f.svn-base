package com.gobestsoft.common.modular.mst.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.mst.model.MstMemberCadre;

/**
 * 会员信息Dao层接口
 * @author zhangdw
 * 2018-05-11 下午4点 10分
 */

public interface MstMemberCadreDao  extends BaseMapper<MstMemberCadre>{
	
	/**
	 * 多条件分页查询
	 * 关联会员信息表进行查询
	 * @param page
	 * @param name
	 * @param deptId
	 * @param memberId
	 * @return
	 */
	public List<Map<String,Object>> selectListPage(
            @Param("page") Page<Map<String, Object>> page,
            @Param("name") String name,
            @Param("deptId") Integer deptId,
            @Param("memberId") Integer memberId);
	
	/**
	 * 根据当前登录用户orgId,查询出所属工会及子工会及部门的会员干部
	 * @param page
	 * @param name
	 * @param orgId
	 * @return
	 */
	public List<Map<String,Object>> selectListPageByOrgId(
            @Param("page") Page<Map<String, Object>> page,
            @Param("name") String name,
            @Param("orgId") Integer orgId);
	
	/**
	 * 根据sys_dept表id
	 * 关联会员信息表进行查询干部列表
     * @param deptId
	 * @return
	 */
	public List<Map<String,Object>> selectListMap(
            @Param("deptId") Integer deptId);
	
	/**
	 * 根据会员id，查询出该会员的干部信息
	 * @param memberId 会员id
	 * @return
	 */
	public List<MstMemberCadre> selectByMemberId(@Param("memberId") Integer memberId);
	
	/**
	 * 根据会员id，查询出该会员的干部信息和会员名称,任职工会名称信息
	 * @param memberId 会员id
	 * @return
	 */
	List<Map<String,Object>> selectMemBerAndMemberCadreByMemberId(@Param("memberId") Integer memberId);

	/**
	 * 根据会员id，公司id和工会Id查询信息是否存在
	 * @param memberId
	 * @param organizationId
	 * @param unionId
	 * @return
	 */
	public List<MstMemberCadre> selectByMemberAndUnion(
            @Param("memberId") Integer memberId,
            @Param("organizationId") Integer organizationId,
            @Param("unionId") Integer unionId);

	/**
	 * 根据会员id,查询出关联的干部数量
	 * @param memberId
	 * @return
	 */
	public Integer selectCountByMemberId(@Param("memberId") Integer memberId);

	/**
	 * 根据会员id,获取关联的多个干部id拼接的字符串,以逗号进行分割
	 * @param memberId
	 * @return
	 */
	public String getIdsByMemberId(@Param("memberId") Integer memberId);
	
	/**
	 * 根据工会组织id,查询出该工会下的干部数量
	 * @param unionId
	 * @return
	 */
	public Integer getMemberCadreCountByUnionId(@Param("unionId") Integer unionId);
	
	/**
	 * 根据sys_dept表id,和工会职务的编码,获取对应工会职务人员名称
	 * @param unionId
	 * @param code
	 * @return
	 */
	String getChairmanNameByDeptId(@Param("unionId") Integer unionId, @Param("code") String code);

	/**
	 * 根据工会组织id,查询会员中干部的数量
	 * @param unionId
	 * @return
	 */
	Integer selectCountByMemberAndUnionId(@Param("unionId") Integer unionId);
	
}
