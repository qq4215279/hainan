package com.gobestsoft.common.modular.statistics.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("sys_tongji_education_key")
@Data
public class SysEducationKey extends Model<SysEducationKey> {

    @Override
    protected Serializable pkVal() {
        return id;
    }

    private Integer id;

    private String name;

    private Integer pid;
}
