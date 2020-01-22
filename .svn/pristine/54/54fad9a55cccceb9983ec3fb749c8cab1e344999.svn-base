package com.gobestsoft.common.modular.opinion.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
/**
 * 建言献策
 */
@Data
@TableName("app_opinion")
public class Opinion extends Model<Opinion>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private String auid;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 联系方式
	 */
	private String contact;

	private Integer type;
	@TableField(exist = false)
	private String typeName;

	private Integer status;
	@TableField(exist = false)
	private String statusName;
	/**
	 * 创建时间
	 */
	private String createTime;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
