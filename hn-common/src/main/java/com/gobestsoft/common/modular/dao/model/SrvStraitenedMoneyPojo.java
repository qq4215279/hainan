package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 困难资金
 */
@TableName("srv_straitened_money")
@Data
public class SrvStraitenedMoneyPojo extends Model<SrvStraitenedMoneyPojo> {

    private Integer id;

    private String rescuedName;

    private Integer age;

    private Integer sex;

    @TableField(exist = false)
    private String sexName;

    private String certificateNum;

    private Integer moneyNum;

    /**
     * 审查状态【0：待处理】【1：通过】【2：拒绝】'
     */
    private Integer status;

    @TableField(exist = false)
    private String statusName;

    private Integer workStatus;

    @TableField(exist = false)
    private String workStatusName;

    private String remark;

    private String createUser;

    private String createTime;

    private Integer delFlg;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
