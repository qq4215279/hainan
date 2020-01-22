package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 法律援助审批日志表
 */
@TableName("srv_law_support_log")
@Data
public class SrvLawSupportLogPojo extends Model<SrvLawSupportLogPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;
    /**
     * 法律援助申请类型：0：中央专项彩票公益金法律援助项目法律援助申请。2：海南省职工服务中心法律援助申请。
     */
    private Integer supportId;
    /**
     * 状态审核状态[1:待审核],[2:通过],[3:拒绝]
     */
    private Integer status;

    /**
     * 审核意见
     */
    private String comment;

    /**
     * 审核日期（格式yyyyMMddhhmmss）可当创建日期使用
     */
    private String checkDate;

    /**
     * 创建用户类型：0:APP用户; 1:系统用户
     */
    private Integer createUserType;

    /**
     * 创建人人uid或auid
     */
    private String createUid;
    /**
     * 创建人名称
     */
    private String createTime;

    /**
     * 审核人id
     */
    private String checkUid;


    /**
     * 审核人的组织id
     */
    private Integer checkDeptId;

    /**
     * 市级审核工会组织id
     */
    private Integer checkleadDeptId;


}
