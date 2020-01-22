package com.gobestsoft.common.modular.dept.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * app用户转会
 * Created by duanmu on 2018/10/16.
 */
@TableName("dept_member_transfer")
@Data
public class DeptMemberTransfer extends Model<DeptMemberTransfer> {

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
     * 会员ID
     */
    private Integer memberId;

    /**
     * app用户ID
     */
    private String auid;

    /**
     * 转出工会（会员当前工会）
     */
    private Integer turnOutDeptId;

    /**
     * 转入工会
     */
    private Integer transferDeptId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * status(申请状态 -1：处理中，0：拒绝，1：通过(字典：lib_approve_status))
     */
    private String status;

    /**
     * outStatus(转出状态 -1：处理中，0：拒绝，1：通过(字典：lib_approve_status))
     */
    private String outStatus;

    /**
     * 转出理由
     */
    private String reason;

    /**
     * 转出类型(0: app转出 1：后台转出)
     */
    private Integer returnFlg;
}
