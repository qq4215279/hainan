package com.gobestsoft.common.modular.law.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/*
常见问题查询表
 */
@Data
@TableName("law_common_problem")
public class LawCommonProblem  extends Model<LawCommonProblem> {
    private Integer id;
    /**
     * 问题标题
     */
    private String title;
    /**
     * 问题描述
     */
    private String content;
    /**
     * 问题创建时间
     */
    private String createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
