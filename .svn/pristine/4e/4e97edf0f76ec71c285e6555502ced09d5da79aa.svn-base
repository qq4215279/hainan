package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by duanmu on 2018/12/26.
 */
@Data
@TableName("dept_del_record")
// 注册且未激活删除记录表
public class DeptDelRecordPojo extends Model<DeptDelRecordPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;

    private Integer dept_id;

    private String account;

    private String operation_time;

    private String operation_user;

}
