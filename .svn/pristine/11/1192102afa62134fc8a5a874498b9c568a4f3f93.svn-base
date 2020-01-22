package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsCategoryMapper extends BaseMapper<CmsCategoryPojo> {

    /**
     * 条件查询，无分页（名称，开始日期，结束日期）
     *
     * @date 2018-09-11
     */
    List<CmsCategoryPojo> selectTopicList(@Param("page") Page<CmsCategoryPojo> page,
                                    @Param("name") String name,
                                    @Param("begDate") String begDate,
                                    @Param("endDate") String endDate,
                                    @Param("type") Integer type
    );

    /**
     * 获取专题信息
     * @param id
     * @return
     */
    CmsCategoryPojo getTopicInfoById(@Param("id") String id);

    /**
     * 更新专题信息
     * @param pojo
     * @return
     */
    int updateTopicInfoById(CmsCategoryPojo pojo);

    /**
     * 删除专题信息
     */
    int delTopic(@Param("id") String id);

    /**
     * 获取列表 0:专题 1:栏目
     */
    List<CmsCategoryPojo> getCategoryList(@Param("type") Integer type);

    /**
     * 根据pid获取id最大值
     */
    int getMaxIdByPid(@Param("pid") int pid);

    /**
     * 插入
     * @return
     */
    Integer insertColumnAll(CmsCategoryPojo pojo);
}