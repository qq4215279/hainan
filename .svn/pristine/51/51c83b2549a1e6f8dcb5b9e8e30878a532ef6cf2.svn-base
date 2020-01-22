package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户积分表
 */
@Data
@TableName("cky_worker")
public class CkyWorkerPojo extends Model<CkyWorkerPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    private Integer id;

    private String type;

    private String name;

    private String summary;

    private String avatar;

    private  String signature;


    private  String recommendReason;


    private  String details;

    private String createUid;

    private String createTime;

}