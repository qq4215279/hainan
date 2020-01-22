package com.gobestsoft.common.modular.dept.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.Contract;
import com.gobestsoft.common.modular.dept.model.ContractInfo;

/**
 * 集体合同Dao
 * @author ke
 *
 */

public interface ContractDao {

	/**
     * 条件查询，无分页（工会编号，工会名称，组织机构代码，是否签订集体合同）
     *
     * @author ke
     * @Date 2017/11/24 13:30
     */
	public List<Map<String,String>> selectByCondition(
            @Param("page") Page<ContractInfo> page,
            @Param("hasGroupContract") Integer hasGroupContract,
            @Param("simpleName") String simpleName,
            @Param("deptNo") String deptNo,
            @Param("orgCode") String orgCode);
	
	public List<Map<String,Object>> selectGetById(@Param("contractId") String contractId);
	
	/**
	 * 根据组织ID修改集体合同信息
	 */
	public void updateByContractId(Contract contract);
}
