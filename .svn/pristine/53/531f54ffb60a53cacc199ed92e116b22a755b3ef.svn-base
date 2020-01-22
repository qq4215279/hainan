package com.gobestsoft.common.modular.dept.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dept.model.AppCheckLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by duanmu on 2018/11/14.
 */
@Repository
public interface AppCheckLogMapper extends BaseMapper<AppCheckLog> {

    /**
     * 多条件分页查询方法
     *
     * @param type
     * @param auid
     * @return
     */
    public List<AppCheckLog> getSelectByInfo(@Param("type") String type,
            @Param("auid") String auid);

    /**
     * 删除app审核流程
     */
    public Integer delCheckLog(@Param("auid") String auid, @Param("type") String type);

    /**
     * 根据身份证号获取app用户会籍类型
     */
    @Select("select * from person_info where certificate_num=${cardNo}")
    Map<String,Object> getUserDetail(@Param("cardNo") String cardNo);
}
