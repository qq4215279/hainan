package com.gobestsoft.common.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * <p>
 * 邮件信息管理表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-12-14
 */
@Data
@TableName("mail_detail")
public class Mail extends Model<Mail> {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
	private Integer id;
    /**
     * 邮件id
     */
	private String mailId;
    /**
     * 主题
     */
	private String subject;
    /**
     * 内容
     */
	private String context;
    /**
     * 发件人ID
     */
	private String sendId;
    /**
     * 发件人名称
     */
	private String sendName;
    /**
     * 收件人
     */
	private String recId;	
    /**
     * 收件人ID
     */
	private String acceptId;
    /**
     * 收件人名称
     */
	private String acceptName;	
    /**
     * 创建时间
     */
	private String creatTime;
    /**
     * 删除标志
     */
	private Integer isDelect;
    /**
     * 草稿标志
     */
	private Integer isDraft;
    /**
     * 状态 0:未读 1:已读 2:删除
     */
	private String status;
    
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Mail{" +
				"id=" + id +
				", mailId=" + mailId +
				", subject=" + subject +
				", context=" + context + 
				", sendId=" + sendId + 
				", sendName=" + sendName + 
				", recId=" + recId + 
				", creatTime=" + creatTime + 
				", isDelect=" + isDelect + 
				", isDraft=" + isDraft + 
				", status=" + status + 
				"}";
	}
}
