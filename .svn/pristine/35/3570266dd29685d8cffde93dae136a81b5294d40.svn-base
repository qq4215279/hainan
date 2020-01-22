package com.gobestsoft.common.modular.dept.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dept.model.DeptMemberTransferLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by duanmu on 2018/10/16.
 */
@Repository
public interface DeptMemberTransferLogMapper extends BaseMapper<DeptMemberTransferLog> {


    /**
     * 根据申请id，获取第一条log日志；即：转入工会时添加的数据
     * @param id
     * @return
     */
    List<Map<String,Object>> selectMapByApplyId(@Param("id") Integer id);
}
