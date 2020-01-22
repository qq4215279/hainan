package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 帮助互助表
 *
 * @author gutongwei
 */
@TableName("srv_straitened_log")
@Data
public class SrvStraitenedLogPojo extends Model<SrvStraitenedLogPojo> {


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;
    /**
     * 日志类型  0:初审；1:终审
     */
    private Integer type;
    
    /**
     * 困难帮扶id
     */
    private Integer straitenedId;


    /**
     * 审核状态[1:待审核],[2:通过],[3:拒绝]
     */
    private Integer status;


    /**
     * 审核意见
     */
    private String comment;


    /**
     * 创建人
     */
    private String createUid;
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 创建用户类型：0:APP用户; 1:系统用户
     */
    private Integer createUserType;

    /**
     * 审核日期
     */
    private String checkDate;

    /**
     * 审核人
     */
    private String checkUid;

    /**
     * 审批组织
     */
    private Integer checkDeptId;

}
