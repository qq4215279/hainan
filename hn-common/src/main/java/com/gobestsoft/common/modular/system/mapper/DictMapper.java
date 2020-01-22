package com.gobestsoft.common.modular.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.common.modular.system.model.DictModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
@Repository
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 根据字典groupCode,查询出字典list集合
     *
     * @param groupCodes
     * @return
     */
    public List<Dict> getDicListByGroupCodes(@Param("groupCodes") String[] groupCodes);


    /**
     * 获取参数字典
     *
     * @param groupCode
     * @param pid
     * @return
     */
    DictModel getDictionary(@Param("groupCode") String groupCode, @Param("pid") int pid);


}