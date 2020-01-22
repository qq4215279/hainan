package com.gobestsoft.common.modular.dao.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName("cms_category")
@Data
public class CmsCategoryPojo extends Model<CmsCategoryPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;

    private Integer pid;

    private String name;

    private String icon;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;

    private Integer delFlg;
}