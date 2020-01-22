package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业资质信息表
 * @author duanmu
 *
 */
@Data
@TableName("dept_enterprise_qualification")
public class QualificationPojo extends Model<QualificationPojo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 上级工会名称
     */
    private String pName;
    /**
     * 登录用户user_id
     */
    private String uid;

    /**
     * 公司名称
     */
    private String enterpriseName;
    /**
     * 经济类型
     */
    private String economicType;

    /**
     * 所属行业
     */
    private String industryType;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 公司规模
     */
    private Integer enterpriseNumber;
    /**
     * 公司介绍
     */
    private String introduce;
    /**
     * 公司网址
     */
    private String website;
    /**
     * 资质路径
     */
    private String qualificationPath;
    /**
     * 审核状态
     */
    private String status;

    private String createUser;

    private String createTime;

    private String updateUser;

    private String updateTime;
    /**
     * 所属行业字典名称
     * 临时存储，用于app调用；不插入数据库
     */
    private String  industryName;
    /**
     * 经济类型字典名称
     * 临时存储，用于app调用；不插入数据库
     */
    private String  economicName;

    protected Serializable pkVal() {
        return this.id;
    }

}
