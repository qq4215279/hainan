package com.gobestsoft.common.modular.homepage.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.homepage.model.IndexTips;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IndexTipsMapper extends BaseMapper<IndexTips> {

    /**
     * 多条件分页查询方法
     * @param page
     * @param tip 小组名称
     * @return
     */
    public List<Map<String,Object>> selectTipList(
            @Param("page") Page<Map<String, Object>> page,
            @Param("tip") String tip);


    public Map<String,Object> selectTip(
            @Param("id") String id);

}
