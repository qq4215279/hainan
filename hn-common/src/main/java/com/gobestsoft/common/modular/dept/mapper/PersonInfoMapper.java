package com.gobestsoft.common.modular.dept.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.PersonInfo;

/**
 * 人员基本信息
 * @author zhangdaowei
 * @date 2018-08-22
 */
@Repository
public interface PersonInfoMapper extends BaseMapper<PersonInfo>{
	
	/**
	 * 企业端会员管理多条件分页查询
	 * @param page
	 * @param name 会员名称
	 * @param deptId 当前登录用户的orgId
	 * @param memberCard 会员卡号
	 * @return
	 */
	List<Map<String, Object>> selectCompanyPageByCondition(@Param("page") Page<Map<String, Object>> page
			, @Param("name")  String name 
			, @Param("deptId") Integer deptId
			, @Param("memberCard") String memberCard);

	/**
	 * 根据身份证号查询该会员信息是否存在
	 * @param certificateNum
	 * @return
	 */
	List<PersonInfo> selectByCertificateNum(@Param("certificateNum") String certificateNum);

	/**
	 * 根据身份证号和名字查询是否已经存在该会员信息
     */
	int selectByNameAndNum(@Param("name") String name, @Param("certificateNum") String certificateNum);

	/**
	 * 根据会员id会员获取是否是农民工
     */
	String selectByMemId(@Param("memberId") Integer id);

}
