package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.MyContributePojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duanmu on 2018/9/20 0020.
 */
@Repository
public interface MyContributeMapper extends BaseMapper<MyContributePojo> {

    /**
     * 我要投稿
     * @param pojo
     */
    void addSubmission(MyContributePojo pojo);

    /**
     * 我的投稿
     * @param rowBounds
     * @param auid
     * @return
     */
    List<MyContributePojo> mySubmission(@Param("page") RowBounds rowBounds, @Param("auid") String auid);

    /**
     * 删除投稿
     */
    void delSubmission(@Param("id") Integer id);
}
