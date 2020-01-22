package com.gobestsoft.common.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author li
 * @since 2018-09-4
 */
@Data
@NoArgsConstructor @AllArgsConstructor
@TableName("sys_message")
public class SysMsg extends Model<SysMsg> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
//	@TableId(value="uid", type= IdType.AUTO)
//	@TableId(value="uid", type= IdType.INPUT)
	private String id;
    /**
     * 内容
     */
	private String content;
    /**
     * 发送时间
     */
	private String createTime;
    /**
     * 工会ID
     */
	private Integer toDeptId;
    /**
     * is_open
     */
	private String isOpen;
    /**
     * 消息类型：0：建会审批。1：入会审批。
     */
	private String type;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	
	@Override
	public String toString() {
		return "SysMsg {id=" + id + ", content=" + content + ", createTime=" + createTime + ", toDeptId=" + toDeptId
				+ ", isOpen=" + isOpen + ", type=" + type + "}";
	}
}
