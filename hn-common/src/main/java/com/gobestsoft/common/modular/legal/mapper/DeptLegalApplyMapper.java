package com.gobestsoft.common.modular.legal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.legal.model.DeptLegalApply;

/**
 * 法人申请
 */
@Repository
public interface DeptLegalApplyMapper extends BaseMapper<DeptLegalApply> {

	/**
	 * 申请类型   0:申请；1:变更；2:注销；3:遗失补办
	 */
	final static String[] A_TYPE = {"0","1","2","3"};
	final static String[] A_TYPE_NAME = {"申请","变更","注销","遗失补办"};
	/**
	 * 申请状态   0:待审核；1:审核中；2:审核通过；3:审核拒绝
	 */
	final static String[] A_STATUS = {"0","1","2","3"};

	/**
	 * 法人资格申请、变更、注销、遗失补办多条件分页查询
	 * @param page
	 * @param name 法人名称
	 * @param agentName 申请时间
	 * @param uid 登录人uid
	 * @return
	 */
	List<Map<String, Object>> selectApplyPageByCondition(@Param("page") Page<Map<String, Object>> page
			, @Param("name") String name
            , @Param("deptName") String deptName
			, @Param("agentName") String agentName
			, @Param("uid") String uid
			, @Param("type") String type);

	/**
	 * 审核页多条件分页查询
	 * @param page
	 * @param name
	 * @param agentName
	 * @param orgId
	 * @return
	 */
	List<Map<String, Object>> selectApplyCheckPageByCondition(@Param("page") Page<Map<String, Object>> page
			, @Param("name") String name
			, @Param("deptName") String deptName
			, @Param("agentName") String agentName
			, @Param("orgId") Integer orgId
		    , @Param("type") String type);

	/**
	 * 根据工会流水号和申请状态查询所有的申请
	 * @param dept_id
	 * @param list
	 * @return
	 */
	List<DeptLegalApply>  selectApplyByStatus(@Param("dept_id") Integer dept_id,@Param("type")Integer type, @Param("list") List<String> list);



}
