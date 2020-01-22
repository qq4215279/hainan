package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


@TableName("app_integral_task_log")
@Data
public class AppIntegralTaskLogPojo extends Model<AppIntegralTaskLogPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;

    private String auid;
    private int taskId;
    private String completeDate;


}
