package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("staff_skill")
@Data
public class StaffSkillPojo  extends Model<StaffSkillPojo> {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     *身份证号
     */
    private String idNum;
    /**
     *电话号
     */
    private String mobile;
    /**
     *技能需求
     */
    private String skillNeed;
    /**
     *回复状态 0未回复  1已回复
     */
    private String replyStatus;
    /**
     *回复内容
     */
    private String replyContent;
    /**
     *创建时间
     */
    private String createTime;
    /**
     *创建用户
     */
    private String createUser;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
