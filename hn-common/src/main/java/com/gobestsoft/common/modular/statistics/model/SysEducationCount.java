package com.gobestsoft.common.modular.statistics.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("sys_tongji_education_count")
@Data
public class SysEducationCount extends Model<SysEducationCount> {

    @Override
    protected Serializable pkVal() {
        return id;
    }

    private Integer id;

    private Integer countNum;
}
