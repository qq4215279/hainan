package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * create by gutongwei
 * on 2018/6/6 上午9:29
 */
@TableName("smp_group_comment")
@Data
public class SmpGroupCommentPojo extends Model<SmpGroupCommentPojo> {

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private int id;

    private String auid;

    private int postId;

    private String text;

    private String createTime;

    private int delFlg;

}
