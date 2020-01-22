package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 创客园-工作室荣誉
 *
 * @author 顾佟伟
 * @date 2018-02-09 14:18:32
 */
@TableName("cky_honor")
@Data
public class CkyHonorPojo extends Model<CkyHonorPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private int id;

    /**
     * 工作室ID
     */
    private int studioId;

    /**
     * 标题名称
     */
    private String title;

    /**
     * 荣誉内容
     */
    private String content;

    /**
     * 展示图片,以“,”分割
     */
    private String images;

    /***
     * 是否用于展示至顶层【0：否】【1：是】
     */
    private int isShow;
    
    private String getTime;

    private String createTime;

    private String createUser;

    private int del_flg;
}
