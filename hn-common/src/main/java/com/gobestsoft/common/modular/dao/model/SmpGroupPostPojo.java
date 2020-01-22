package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * create by gutongwei
 * on 2018/6/6 上午9:29
 */
@TableName("smp_group_post")
@Data
public class SmpGroupPostPojo extends Model<SmpGroupPostPojo> {

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private int id;

    private int groupId;

    private String auid;

    private String title;

    private String text;

    private String images;

    private String createTime;

    private int delFlg;

}
