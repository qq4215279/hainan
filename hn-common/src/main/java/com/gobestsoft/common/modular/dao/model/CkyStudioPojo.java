package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 创客园-工作室表
 *
 * @author 顾佟伟
 * @date 2018-02-09 14:18:32
 */
@TableName("cky_studio")
@Data
public class CkyStudioPojo extends Model<CkyStudioPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private int id;
    /**
     * 工作室名称
     */
    private String name;
    /**
     * 工作室封面图
     */
    private String cover;
    /**
     * 轮播图
     */
    private String banner;
    /**
     * 工作室概括
     */
    private String summary;

    private String createTime;

    private String createUser;

    private int delFlg;
}
