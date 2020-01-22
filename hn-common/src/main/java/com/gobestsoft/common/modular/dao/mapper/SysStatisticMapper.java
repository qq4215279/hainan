package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.SysStatisticPojo;
import org.springframework.stereotype.Repository;

@Repository
public interface SysStatisticMapper extends BaseMapper<SysStatisticPojo> {


    void bigData_func();
}
