package com.gobestsoft.common.modular.opinion.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 建言献策
 */
@Data
@TableName("app_opinion_log")
public class OpinionLog extends Model<OpinionLog>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;

	private Integer opinionId;

	private String replyContent;

	private String replyTime;

	private Integer status;

	private String createDate;

	private String createUser;

	private Integer orgId;

	private String fromPerson;

	private Integer fromDept;

	private String toPerson;

	private Integer toDept;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
