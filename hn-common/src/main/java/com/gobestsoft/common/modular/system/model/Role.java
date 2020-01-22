package com.gobestsoft.common.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
@Data
@TableName("sys_role")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 父角色id
     */
    @NotNull(message = "上级角色不能为空")
    private Integer pid;
    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    private String name;
    /**
     * 提示
     */
    @NotEmpty(message = "别名不能为空")
    private String tips;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", sort=" + sort +
                ", pid=" + pid +
                ", name=" + name +
                ", tips=" + tips +
                "}";
    }
}
