package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.SrvLawSupportLogPojo;
import com.gobestsoft.common.modular.model.LogModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SrvLawSupportLogMapper extends BaseMapper<SrvLawSupportLogPojo> {

	/**
	 * 根据申请id,查询审核日志
	 * @param id
	 * @return
	 */
	List<LogModel> selectListMapById(@Param("id") Integer id);


}