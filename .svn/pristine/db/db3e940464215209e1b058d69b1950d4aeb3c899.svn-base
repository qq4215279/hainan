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
@TableName("cms_course_operation_log")
@Data
public class CmsCourseOperationPojo extends Model<CmsCourseOperationPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**  */
    private int id;
    /**  */
    private int course_id;

    private String auid;

    /**
     * 操作类型:[0:点赞][1:分享][2:收藏][3:浏览]
     */
    private int type;

    private String createTime;

}
