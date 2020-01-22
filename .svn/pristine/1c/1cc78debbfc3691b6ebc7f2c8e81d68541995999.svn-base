package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.AppMessagePojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface AppMessageMapper extends BaseMapper<AppMessagePojo> {

    /**
     * 获取用户消息列表
     *
     * @param auid      用户ID
     * @param rowBounds
     * @return
     */
    List<Map<String, Object>> getMessagesByAuid(@Param("categoryId") Integer categoryId,
                                                @Param("auid") String auid,
                                                @Param("createTime") String createTime,
                                                @Param("page") RowBounds rowBounds);

    /**
     * 获取用户未读消息数量
     *
     * @param auid
     * @return
     */
    Integer getNotReadMessagesByAuid(@Param("auid") String auid,@Param("createTime") String createTime);


    /**
     * 获取用户的激光推送账号
     *
     * @param auids
     * @return
     */
    String[] getRegisterIds(@Param("auids") String[] auids);


    /**
     * 获取所以消息类型
     *
     * @return
     */
    List<Map<String, String>> getAllMessageCategory();

    /**
     * 获取消息对应的appUser
     *
     * @param messageId
     * @return
     */
    List<Map<String, Object>> getMessageRelationAppUser(@Param("messageId") int messageId);

    List<Map<String, String>> getAllMessage(@Param("page") Page<Map<String, String>> page, @Param("categoryId") Integer categoryId, @Param("appoint") Integer appoint);


    /**
     * 获取消息分类
     *
     * @param auId
     * @return
     */
    List<Map<String, Object>> getUserMessageCategory(@Param("auid") String auId, @Param("createTime") String createTime);


    /**
     * 获取最新一条未读消息
     *
     * @param auId
     * @param createTime
     * @param categoryId
     * @return
     */
    AppMessagePojo getLastMessage(@Param("auid") String auId, @Param("createTime") String createTime, @Param("categoryId") Integer categoryId);
}