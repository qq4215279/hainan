package com.gobestsoft.common.modular.dao.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("xlyz_master")
public class XlyzMasterPojo extends Model {

    private String id;

    private String  name;

    private String  sex;

    private String  avatar;

    private String  summary;

    private String  introduce;

    private Integer  delFlag;

    private String  sort;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
