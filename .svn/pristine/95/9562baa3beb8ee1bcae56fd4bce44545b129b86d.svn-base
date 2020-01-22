package com.gobestsoft.common.modular.dept.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 入会邀请表
 * @author  xiatao
 */
@Data
@TableName("app_invite_dept")
public class AppInviteDept extends Model<AppInviteDept>{

    private static final long serialVersionUID = 1L;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * app用户ID
     */
    private String auid;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 是否建会 (0: 未建会  1: 已建会)
     */
    private Integer isDept;

    @Override
    public String toString() {
        return "AppInviteDept{" +
                "id=" + id +
                ", auid='" + auid + '\'' +
                ", name='" + name + '\'' +
                ", unitName='" + unitName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isDept=" + isDept +
                '}';
    }
}
