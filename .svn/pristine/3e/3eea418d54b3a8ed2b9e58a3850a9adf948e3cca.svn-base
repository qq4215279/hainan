package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedMoneyPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 相互帮扶
 */
@Repository
public interface SrvStraitenedMoneyMapper extends BaseMapper<SrvStraitenedMoneyPojo> {


	List<Map<String, Object>> selectByCondition(
			@Param("page") Page<Map<String, Object>> page
			,@Param("name") String name
			,@Param("status") Integer status
	);
}