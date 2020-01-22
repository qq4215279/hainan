package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.SmpGroupPostPojo;
import com.gobestsoft.common.modular.smp.model.SmpGroupPostEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * create by gutongwei
 * on 2018/6/6 上午9:33
 */
@Repository
public interface SmpGroupPostMapper extends BaseMapper<SmpGroupPostPojo> {

    /**
     * 获取帖子列表
     * create by gutongwei
     * on 2018/6/6
     *
     * @param rowBounds
     * @param groupId
     * @return
     */
    List<SmpGroupPostEntity> getGroupPosts(@Param("page") RowBounds rowBounds, @Param("groupId") Integer groupId);

    /**
     * 获取帖子详细
     * create by gutongwei
     * on 2018/6/6
     *
     * @param postId
     * @return
     */
    Map<String, Object> postDetail(@Param("postId") int postId);

    /**
     * 获取帖子回复
     *
     * @param postId
     * @param rowBounds
     * @return
     */
    List<Map<String, Object>> getPostReply(@Param("postId") int postId, @Param("page") RowBounds rowBounds);

}
