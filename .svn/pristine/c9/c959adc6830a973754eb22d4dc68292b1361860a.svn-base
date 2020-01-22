package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * Banner管理表
 *
 * @author caoy
 * @date 2018-02-09 14:18:32
 */
@TableName("app_integral_task")
@Data
public class AppIntegralTaskPojo extends Model<AppIntegralTaskPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * Banner ID
     */
    private Integer id;

    /**
     * 任务类型：0：终生任务。1：每日任务
     */
    private int type;


    /**
     * 频率
     */
    private int frequency;

    private int integral;

    private String taskName;

    private String createTime;
    private String createBy;
    private String avatar;
    private String completeTip;
    private String uncompleteTip;


}
