package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 课程表
 *
 * @author gtw
 * @date 2018-06-15
 */
@TableName("cms_course")
@Data
public class CmsCoursePojo extends Model<CmsCoursePojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**  */
    private int id;
    /**  */
    private int pid;
    /**
     * 【0:普通课程】【1：系列课程】
     */
    private int type;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图路径
     */
    private String cover;
    /**
     * 内容
     */
    private String content;
    /**
     * 附件，以“,”分割
     */
    private String attachment;
    /**
     * 课程类型类型:0：图文，1：音频，2：视频，3：跳转 4：文件（保留）
     */
    private int infoType;
    /**
     * 状态  【0：草稿】 ，【1：上线】，【2:下架】
     */
    private int status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 组织id
     */
    private Integer orgId;
    private String createUser;
    /**  */
    private String createTime;
    /**  */
    private String updateUser;
    /**  */
    private String updateTime;

    private Integer delFlg;

}
