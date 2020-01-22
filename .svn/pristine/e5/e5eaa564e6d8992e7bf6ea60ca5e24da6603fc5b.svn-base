package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedPojo;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.common.modular.srv.StraitenedEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 相互帮扶
 */
@Repository
public interface SrvStraitenedMapper extends BaseMapper<SrvStraitenedPojo> {

	/**
	 * 根据条件查询困难帮扶终审列表
	 * @param page
	 * @param name
	 * @param status
	 * @param type
	 * @param workUnit
	 * @param deptId
	 * @return
	 */
	List<Map<String, Object>> selectByCondition(
			@Param("page") Page<Map<String,Object>> page, 
			@Param("name") String name,
			@Param("status") Integer status,
			@Param("type") Integer type,
			@Param("workUnit") String workUnit,
			@Param("deptId") Integer deptId);
	
	/**
	 * 帮扶详情 
	 * create by xiashasha
	 * on 2018/09/13 下午03:21
	 * @param id
	 * @return
	 */
	Map<String, Object> straitenedDetail(@Param("id") int id);

	/**
	 * 终审列表
     */
	public List<StraitenedEntity> getStraitened(@Param("page") RowBounds rowBounds, @Param("auid") String auid);

	Map<String,Object> selectInfoById(@Param("id")String id);

	List<Map<String,Object>> selectContacts(@Param("target_id")String  target_id,@Param("type")Integer type);

	/**
	 * 根据终审流水号查询医疗救助信息
	 * @param id
	 * @return
	 */
	List<Map<String,Object>> selectMedicalList(@Param("id") String id);

	/**
	 * 根据终审流水号查询助学信息
	 * @param id
	 * @return
	 */
	List<Map<String,Object>> selectStudyList(@Param("id") String id);

	/**
	 * 更新待审核审核人流水号，转办
	 * @param straitened_id
	 * @param transfer_dept_id
	 * @param transfer_reason
	 */
	int updateAuditDeptId(@Param("straitened_id")Integer straitened_id
			,@Param("transfer_dept_id")Integer transfer_dept_id
			,@Param("transfer_reason")String transfer_reason
			,@Param("type")Integer type);


	/**
	 * 查询指定天数前申请而且至今没有操作审核的记录
	 * @param day
	 * @return
	 */
	List<Map<String,Object>> selectExpireAudit(@Param("day") int day);

	void updateExpireAudit(List<String> list);

	void updateExpireLog(List<String> list);

	/**
	 * 查询建档人员的会员信息
	 * @param auid
	 * @return
	 */
	Map<String ,Object >selectMemberByAuId(@Param("id") String auid);

	List<Map<String,Object>> getStraitenedAll(@Param("page") RowBounds rowBounds,
											 ObjectMap query
											  );

	/**
	 * 查询最新的日志
	 * @param list
	 * @return
	 */
	List<Map<String,Object>>selectLogIdList(List<String> list);


	Integer selectStraitenedIdByStudyId(@Param("id") int id);

	Integer selectStraitenedIdByMedicalId(@Param("id") int id);
}