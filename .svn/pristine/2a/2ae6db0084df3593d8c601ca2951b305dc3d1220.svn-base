package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.SrvLawConsultationLogPojo;
import com.gobestsoft.common.modular.dao.model.SrvLawConsultationPojo;
import com.gobestsoft.common.modular.model.LogModel;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SrvLawConsultationLogMapper extends BaseMapper<SrvLawConsultationLogPojo> {
	
	public Integer selBackId(Integer consultationId);
	
	List<LogModel> logList(Integer id);

}