package com.gobestsoft.common.modular.dept.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 工会信息管理的dao
 *
 * @author cxl
 * @date 2017-11-24
 */
@Repository
public interface MemberDao {

	/**
	 * 根据条件查询会员信息管理列表
	 *
	 * @return
	 * @date 2017年11月26日 21:50:34
	 */
	public List<Map<String, Object>> selectByCondition(@Param("page") Page<Map<String, Object>> page, @Param("unitName") String unitName,
                                                       @Param("orgCode") Integer orgCode, @Param("orgId") Integer orgId);

	/**
	 * 根据条件查询会员信息
	 *
	 * @param ouid
	 * @return
	 * @date 2017年11月26日 21:50:34
	 */
	public Member getDetail(String ouid);
	
	/**
	 * 查询会员信息是否存在
	 *
	 * @param deptId
	 * @return
	 * @date 2017年11月26日 21:50:34
	 */
	public Member getOuidbyMemberCode(String memberCode);

	/**
	 * 增加会员信息
	 *
	 * @param member
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer addMember(Member member);


	/**
	 * 修改会员信息
	 *
	 * @param member
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer editMember(Member member);


	/**
	 * 执行更新会员统计信息的存储过程
	 */
	void updateMemberCountTable1();
	void updateBindMemberCountTable1();
	void updateMemberCountTable2();


}
