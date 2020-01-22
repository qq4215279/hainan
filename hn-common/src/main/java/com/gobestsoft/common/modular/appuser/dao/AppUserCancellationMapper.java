package com.gobestsoft.common.modular.appuser.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.appuser.model.AppUserCancellation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserCancellationMapper extends BaseMapper<AppUserCancellation> {

    /**
     * 获取字典name
     * @param type
     * @return
     */
    String getDictionarieName(@Param("type") Integer type);
}
