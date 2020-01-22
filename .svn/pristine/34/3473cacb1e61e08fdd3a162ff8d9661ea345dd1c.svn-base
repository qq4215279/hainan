package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.SrvContactsPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SrvContactsMapper extends BaseMapper<SrvContactsPojo> {

    /**
     * 插入列表
     *
     * @param pojos
     * @return
     */
    Integer insertAll(@Param("pojos") List<SrvContactsPojo> pojos);


    List<SrvContactsPojo> contactsList (@Param("targetId") Integer targetId,@Param("type") Integer type);
}