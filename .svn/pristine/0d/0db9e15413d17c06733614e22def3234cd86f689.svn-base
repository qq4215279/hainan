package com.gobestsoft.common.modular.homepage.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("app_index_tip")
public class IndexTips extends Model<IndexTips> {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(value="id",type= IdType.AUTO)
    private String id;
    /**
     *招投类型
     */
    private Integer type;

    /**提示栏*/
    private String tip;
    /**
     * 跳转路径
     */
    private String jumpUrl;
    /**
     * 小组ID
     */
    private String groupId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "";
    }

}
