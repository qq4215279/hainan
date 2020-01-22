package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 创客园-话题表
 * create by gutongwei
 * on 2018/6/6 上午9:29
 */
@TableName("cky_topic")
@Data
public class CkyTopicPojo extends Model<CkyTopicPojo> {

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private int id;

    private String auid;

    private String title;

    private String text;

    private String images;

    private String createTime;

    private int delFlg;

}
