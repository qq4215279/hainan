package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * Banner管理表
 *
 * @author caoy
 * @date 2018-02-09 14:18:32
 */
@TableName("app_message")
@Data
public class AppMessagePojo extends Model<AppMessagePojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * Banner ID
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
    /**
     * 图片路径（相对）
     */
    private Integer operationMode;

    /**
     * 操作对象
     */
    private String operation;

    /**
     * 上线状态 0：下线，1：上线
     */
    private Integer appoint;

    /**
     * 是否推送
     */
    private Integer isPush;

    /**
     * 消息类型
     */
    private Integer categoryId;


    /**  */
    private String createTime;
    /**  */
    private String createUser;


}
