package com.gobestsoft.common.modular.dept.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by duanmu on 2018/10/16.
 */
@TableName("dept_member_transfer_log")
@Data
public class DeptMemberTransferLog extends Model<DeptMemberTransferLog> {

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
     * 申请ID
     */
    private Integer applyId;

    /**
     * 审核状态
     */
    private String applyStatus;

    /**
     * 流程步骤
     */
    private String recordFlowInfo;

    /**
     * 审核意见
     */
    private String content;

    /**
     * App用户申请入会的组织id
     */
    private Integer createDeptId;

    /**
     * 下一步审核人所在组织ID
     */
    private Integer nextCheckDeptId;

    /**
     * 创建人uid
     */
    private String createUid;

    /**
     * 创建时间
     */
    private String createTime;

}
