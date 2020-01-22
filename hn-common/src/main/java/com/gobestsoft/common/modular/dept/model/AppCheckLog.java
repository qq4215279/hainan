package com.gobestsoft.common.modular.dept.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * app流程审核表
 * Created by duanmu on 2018/11/14.
 */
@Data
@TableName("app_check_log")
public class AppCheckLog extends Model<AppCheckLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private String auid;

    /**
     * 类型(1: 入会审核  2: 转会审核)
     */
    private String type;

    /**
     * 内容
     */
    private String comment;

    /**
     * 流程步骤
     */
    private String recordFlowInfo;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 颜色状态
     */
    private String status;

    /**
     * 样式状态
     */
    private String styleStatus;

    /**
     * 是否删除
     */
    private Integer delFlg;

    /**
     * 邀请人数
     */
    private Integer inviteNum;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
