
package com.gobestsoft.common.modular.smp.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
/**
 * 秀吧记录表
 * @author zhangdw
 * 2018年6月5日 18:04
 */

@Data
@TableName("smp_show")
public class Show extends Model<Show>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 类型【0：纯文本】【1：图】【2：视频】【3：音频】
	 */
	private Integer fileType;
	/**
	 * 文件路径;多个文件中间用逗号隔开
	 */
	private String fileUrl;
	/**
	 * 内容
	 */
	private String text;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 用户UID
	 */
	private String auid;
	/**
	 * 逻辑删除（0：未删除。1：已删除）
	 */
	private Integer delFlg;
	/**
	 * 秀吧封面图
	 */
	private String coverMap;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	@Override
	public String toString(){
		return "Show [id=" + id + ", fileType=" + fileType + ", fileUrl=" + fileUrl +", text=" + text
				+", createTime=" + createTime +", auid=" + auid +", delFlg=" + delFlg +"]";
	}

}
