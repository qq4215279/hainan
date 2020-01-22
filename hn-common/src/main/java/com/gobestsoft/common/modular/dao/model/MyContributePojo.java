package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * Created by duanmu on 2018/9/20.
 */
@TableName("my_contribute")
@Data
public class MyContributePojo extends Model<MyContributePojo>{

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 投稿id
     */
    private Integer id;

    /**
     * app用户id
     */
    private String auid;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片地址
     */
    private String imgPath;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 投稿状态 -1:审核中；0:未采纳； 1:已采纳
     */
    private Integer status;

    /**
     * 审核意见
     */
    private String checkDesc;

    /**
     * 审核时间
     */
    private String checkDate;

    /**
     * 审核者
     */
    private String checkUser;
}
