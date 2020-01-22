package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * app用户入会、转会、办卡申请表
 * @author zhangdaowei 2018年08月21日 17点33
 */
@TableName("dept_member_apply")
@Data
public class DeptMemberApply extends Model<DeptMemberApply> {
	
	private static final long serialVersionUID = 1L;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    /**
     * ID
     */
//    @TableId(type = IdType.INPUT)
    private Integer id;

    /**
     * app用户ID
     */
    private String auid;

    /**
     * 申请状态 -1：处理中，0：拒绝，1：通过(字典：lib_approve_status)
     */
    private String applyStatus;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 单位名称（仅供预备会员使用）
     */
    private String unitName;

    /**
     * 人员id
     */
    private Integer personId;

    /**
     * 组织id
     */
    private Integer deptId;

}