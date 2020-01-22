package com.gobestsoft.common.modular.appuser.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注销记录表
 * @author xiat
 * @time 2018-12-06 19:00
 */
@Data
@TableName("app_user_cancellation")
public class AppUserCancellation extends Model<AppUserCancellation>{

    private static final long serialVersionUID = 1L;

    @TableId(type=IdType.INPUT)
    private String auid;

    private String account;

    private String nickName;

    private Integer type;

    private String reason;

    private String createTime;

    @Override
    protected Serializable pkVal() {
        return this.auid;
    }
}
