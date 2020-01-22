package com.gobestsoft.common.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
@Data
@TableName("sys_dict")
public class Dict extends Model<Dict> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父级字典
     */
    private Integer pid;
    /**
     * 名称
     */
    private String name;

    /**
     * 组识别码
     */
    private String groupCode;
    /**
     * 代码
     */
    private String code;
    /**
     * 选中标示
     */
    @TableField(exist = false)
    private int selected;

    /**
     * 2018-02-08 caoy add ---- end
     */

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	@Override
	public String toString() {
		return "Dict [id=" + id + ", sort=" + sort + ", pid=" + pid + ", name=" + name + ", groupCode=" + groupCode
				+ ", code=" + code + ", selected=" + selected + "]";
	}

}
