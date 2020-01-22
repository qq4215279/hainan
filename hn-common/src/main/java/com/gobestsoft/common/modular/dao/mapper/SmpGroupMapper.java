package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.SmpGroupPojo;
import com.gobestsoft.common.modular.smp.model.SmpGroupDetailEntity;
import com.gobestsoft.common.modular.smp.model.SmpGroupMemberEntity;
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
public interface SmpGroupMapper extends BaseMapper<SmpGroupPojo> {

    /**
     * 获取小组详细
     * create by gutongwei
     * * on 2018/6/6
     *
     * @param groupId
     * @param auid    用户ID
     * @return
     */
    SmpGroupDetailEntity groupDetail(@Param("groupId") int groupId, @Param("auid") String auid);

    /**
     * 小组列表
     * create by gutongwei
     * on 2018/6/5
     *
     * @param keyword    查询关键字
     * @param auid       用户ID
     * @param auid       orgId
     * @param searchType 查询类型【0：所有小组列表（默认）】【1：热门小组列表】【2：我加入的小组列表】 【3：我创建的小组列表】
     * @return
     */
    List<Map<String, Object>> groups(@Param("keyword") String keyword, @Param("searchType") int searchType,
                                     @Param("auid") String auid, @Param("orgId") Integer orgId, @Param("page") RowBounds rowBounds);

    /**
     * 会员列表
     * create by gutongwei
     * on 2018/6/6
     *
     * @param groupId    小组ID
     * @param memberType 【0：组长】【1：组员】
     * @return
     */
    List<SmpGroupMemberEntity> getGroupMembers(@Param("groupId") int groupId, @Param("memberType") Integer memberType);

    /**
     * 删除小组会员/退出小组
     * create by gutongwei
     * on 2018/6/6
     *
     * @param groupId 小组ID
     * @param auid    用户ID
     * @return
     */
    Integer removeMember(@Param("groupId") int groupId, @Param("auid") String auid);

    /**
     * 小组是否置顶
     * create by gutongwei
     * on 2018/6/6
     *
     * @param groupId 小组ID
     * @param auid    用户ID
     * @return
     */
    Integer groupIsTopOn(@Param("groupId") int groupId, @Param("auid") String auid);

    /**
     * 小组设置为置顶
     * create by gutongwei
     * on 2018/6/6
     *
     * @param groupId 小组ID
     * @param auid    用户ID
     */
    void setTopOn(@Param("groupId") int groupId, @Param("auid") String auid);

    /**
     * 小组取消设置为置顶
     * create by gutongwei
     * on 2018/6/6
     *
     * @param groupId 小组ID
     * @param auid    用户ID
     */
    void setTopOff(@Param("groupId") int groupId, @Param("auid") String auid);

    /**
     * 加入小组
     * create by gutongwei
     * on 2018/6/6
     *
     * @param auid       用户ID
     * @param groupId    小组ID
     * @param createTime 加入时间
     * @return
     */
    Integer joinGroup(@Param("auid") String auid, @Param("groupId") int groupId, @Param("createTime") String createTime);

    /**
     * 是否在小组中
     * create by gutongwei
     * on 2018/6/6
     *
     * @param auid    用户ID
     * @param groupId 小组ID
     * @return
     */
    Integer isInGroup(@Param("auid") String auid, @Param("groupId") int groupId);

    /**
     * 小组类型
     * create by gutongwei
     * on 2018/6/8
     *
     * @return
     */
    List<Map<String, String>> groupsTypes();


    /**
     * 小组会员数量
     *
     * @param groupId
     * @return
     */
    Integer groupMemberNumber(@Param("groupId") int groupId);
}
