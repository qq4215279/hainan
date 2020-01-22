package com.gobestsoft.common.modular.homepage.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by duanmu on 2018/9/25.
 */
@Data
@TableName("app_home_dialog")
public class AppHomeDialog extends Model<AppHomeDialog> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 弹窗id
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片展示
     */
    private String showImg;
    /**
     * 跳转地址
     */
    private String jumpUrl;
    /**
     * 是否强制(0:强制 1:不强制)
     */
    private Integer isForce;
    /**
     * 是否展示(0:展示 1:不展示)
     */
    private Integer isShow;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private String updateTime;
}
