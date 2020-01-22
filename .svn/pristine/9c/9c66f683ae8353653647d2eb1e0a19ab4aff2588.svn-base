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
@TableName("sys_import_log")
@Data
public class SysImportLogPojo extends Model<SysImportLogPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * Banner ID
     */
    private Integer id;

    private String downLink;

    private Integer type;


    private String remark;

    private String createTime;
    /**  */
    private String createUid;
    /**
     * 上线状态 0：下线，1：上线
     */
    private Integer status;


}
