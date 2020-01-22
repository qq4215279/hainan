package com.gobestsoft.common.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.system.model.Mail;
import com.gobestsoft.core.node.UserNode;

/**
 * 邮件信息的dao
 *
 * @author cxl
 * @date 2017-12-14
 */
public interface MailDao {

	/**
	 * 根据条件查询（收件箱）
	 *
	 * @return
	 * @date 2017年12月14日 15:50:34
	 */
	public List<Map<String, Object>> searchForInbox(@Param("page") Page<Map<String, Object>> page
            , @Param("content") String content, @Param("startDate") String startDate, @Param("endDate") String endDate,
                                                    @Param("userId") String userId);
	/**
	 * 根据条件查询（已发送）
	 *
	 * @return
	 * @date 2017年12月15日 11:11:52
	 */
	public List<Map<String, Object>> searchForSendbox(@Param("page") Page<Map<String, Object>> page
            , @Param("content") String content, @Param("startDate") String startDate, @Param("endDate") String endDate,
                                                      @Param("userId") String userId);
	/**
	 * 根据条件查询（已删除）
	 *
	 * @return
	 * @date 2017年12月15日 11:11:52
	 */
	public List<Map<String, Object>> searchForDeletebox(@Param("page") Page<Map<String, Object>> page
            , @Param("content") String content, @Param("startDate") String startDate, @Param("endDate") String endDate,
                                                        @Param("userId") String userId);
	/**
	 * 根据条件查询（草稿箱）
	 *
	 * @return
	 * @date 2017年12月15日 11:11:52
	 */
	public List<Map<String, Object>> searchForDraftbox(@Param("page") Page<Map<String, Object>> page
            , @Param("content") String content, @Param("startDate") String startDate, @Param("endDate") String endDate,
                                                       @Param("userId") String userId);
	
	/**
	 * 根据条件查询邮件信息
	 *
	 * @param ouid
	 * @return
	 * @date 2017年12月15日 14:43:44
	 */
	public Mail getDetail(@Param("mailId") String mailId, @Param("userId") String userId);
	
	/**
	 * 根据条件查询邮件信息（草稿箱）
	 *
	 * @param ouid
	 * @return
	 * @date 2017年12月15日 14:43:44
	 */
	public Mail getDetailDraftbox(@Param("mailId") String mailId);
	
	/**
	 * 获取userTree的节点列表
	 */
	public List<UserNode> userTree();
	
	/**
	 * 查询用户信息
	 *
	 * @param ouid
	 * @return
	 * @date 2017年12月15日 14:43:44
	 */
	public List<Map<String, Object>> getUser();
	
	/**
	 * 查询用户姓名
	 *
	 * @param ouid
	 * @return
	 * @date 2017年12月15日 14:43:44
	 */
	public String getUserName(@Param("uid") String uid);
	
	/**
	 * 增加邮件信息
	 *
	 * @param mail
	 * @return
	 * @date 2017年12月18日 19:43:40
	 */
	public Integer addMail(Mail mail);
	
	/**
	 * 增加邮件信息
	 *
	 * @param mail
	 * @return
	 * @date 2017年12月18日 19:43:40
	 */
	public Integer addMailDetail(Mail mail);
	
	/**
	 * 修改草稿邮件信息
	 *
	 * @param mail
	 * @return
	 * @date 2017年12月19日 9:43:40
	 */
	public Integer editMailDetail(Mail mail);
	
	/**
	 * 邮件状态修改
	 *
	 * @param mail
	 * @return
	 * @date 2017年12月19日 9:43:40
	 */
	public Integer editMail(@Param("status") String status, @Param("mailId") String mailId, @Param("acceptId") String acceptId);

	
	/**
	 * 邮件detail状态修改
	 *
	 * @param mail
	 * @return
	 * @date 2017年12月19日 9:43:40
	 */
	public Integer editMailDetail(@Param("isDelect") Integer isDelect, @Param("mailId") String mailId);
	
	/**
	 * 增加邮件信息
	 *
	 * @param mail
	 * @return
	 * @date 2017年12月19日 11:08:24
	 */
	public Integer updateMailDetail(Mail mail);
	
	/**
	 * 删除草稿邮件信息
	 *
	 * @param mail
	 * @return
	 * @date 2017年12月19日 11:08:24
	 */
	public Integer deleteMailDetail(@Param("mailId") String mailId);

}
