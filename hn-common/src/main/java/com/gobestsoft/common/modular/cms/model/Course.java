package com.gobestsoft.common.modular.cms.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author duanmu
 * @since 2018-06-23
 */
@Data
@TableName("cms_course")
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    private Integer id;
    /**
     * 如果是系列课程，不是大提纲为0. 为目录则为提纲ID
     */
    private Integer pid;
    /**
     * 课程类型
     */
    private Integer type;
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
     * 附件
     */
    private String attachment;
    /**
     * 课程附件类型
     */
    private Integer infoType;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 组织id
     */
    private Integer orgId;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * del_flg:0：未删除，1已删除
     */
    private Integer delFlg;

    /**
     * 可分享
     */
    private Integer isShare;

    /**
     * 可评论
     */
    private Integer isTopic;

    /**
     * 评论是否需要审核
     */
    private Integer isTopicCheck;

    /**
     * 可点赞
     */
    private Integer isLike;

    /**
     * 可收藏
     */
    private Integer isCollect;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
