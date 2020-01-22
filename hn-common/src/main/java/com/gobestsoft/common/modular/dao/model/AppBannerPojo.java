package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.io.Serializable;


/**
 * Banner管理表
 *
 * @author caoy
 * @date 2018-02-09 14:18:32
 */
@TableName("app_banner")
@Data
public class AppBannerPojo extends Model<AppBannerPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * Banner ID
     */
    private Integer id;

    private String targetId;

    private String jumpUrl;

    private String smallJumpUrl;

    private Integer type;

    private String category;

    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String cover;

    private String createTime;
    /**  */
    private String createUid;
    /**
     * 上线状态 0：下线，1：上线
     */
    private Integer status;

    private Integer sort;

    /**
     * 用户权限 0：游客，1：用户， 2：会员
     */
    private String auth;

}
