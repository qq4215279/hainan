package com.gobestsoft.common.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by duanmu on 2018/12/25.
 */
@Data
@TableName("sys_mail")
public class SysMail extends Model<SysMail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 账号
     */
    private String account;
    /**
     * 验证码
     */
    private String verification;
    /**
     * 内容
     */
    private String createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysMail{" +
                "id=" + id +
                ", account=" + account +
                ", verification=" + verification +
                ", createTime=" + createTime +
                "}";
    }
}
