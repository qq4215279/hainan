package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedLogPojo;
import com.gobestsoft.common.modular.model.LogModel;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 相互帮扶
 */
@Repository
public interface SrvStraitenedLogMapper extends BaseMapper<SrvStraitenedLogPojo> {

	/**
	 * 查看审核流程
	 * @param straitenedId
	 * @param type
	 * @return
	 */
	List<LogModel> selectListMapById(@Param("straitenedId") Integer straitenedId, @Param("type") int type);
	List<LogModel> selectListMapRealById(@Param("straitenedId") Integer straitenedId);



}