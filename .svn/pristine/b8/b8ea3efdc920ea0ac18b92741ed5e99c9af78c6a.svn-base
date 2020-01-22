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
@TableName("app_message_relation")
@Data
public class AppMessageRelationPojo extends Model<AppMessageRelationPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;
    /**
     * 标题
     */
    private Integer messageId;

    /**
     * 内容
     */
    private String auid;
    /**
     * 图片路径（相对）
     */
    private Integer isOpen;

    /**
     * 操作对象
     */
    private String openTime;

    /**
     * 上线状态 0：下线，1：上线
     */
    private Integer delFlg;

}
