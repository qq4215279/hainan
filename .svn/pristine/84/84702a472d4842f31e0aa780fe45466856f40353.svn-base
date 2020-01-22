package com.gobestsoft.common.modular.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 用户积分表
 */
@Data
@TableName("app_integral")
public class AppIntegralPojo extends Model<AppIntegralPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    private Integer id;

    private String auid;

    private int type;

    private String targetId;

    private  int value;

    private String createTime;

}