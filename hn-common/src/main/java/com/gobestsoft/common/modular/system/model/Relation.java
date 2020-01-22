package com.gobestsoft.common.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
@Data
@TableName("sys_relation")
public class Relation extends Model<Relation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 菜单id
     */
	private Integer menuid;
    /**
     * 角色id
     */
	private Integer roleid;
	
	private String uid;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Relation [id=" + id + ", menuid=" + menuid + ", roleid=" + roleid + ", uid=" + uid + "]";
	}

	/**
	 * @param menuid
	 * @param roleid
	 * @param uid
	 */
	public Relation(Integer roleid, String uid) {
		super();
		this.roleid = roleid;
		this.uid = uid;
	}

	public Relation() {
		super();
	}

}
