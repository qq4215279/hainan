package com.gobestsoft.common.modular.smp.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
/**
 * 秀吧点赞表
 * @author zhangdw
 * 2018年6月5日 18:15
 */

@Data
@TableName("smp_show_thumbup")
public class ShowThumbup extends Model<ShowThumbup>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 秀吧记录id
	 */
	private Integer showId;
	/**
	 * 用户id
	 */
	private String auid;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
