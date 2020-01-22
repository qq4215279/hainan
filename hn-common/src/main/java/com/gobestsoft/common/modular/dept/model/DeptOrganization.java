package com.gobestsoft.common.modular.dept.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 组织信息表
 *
 * @author xss
 * @date 2018-08-15
 */
@Data
@TableName("dept_organization")
public class DeptOrganization extends Model<DeptOrganization> {

    private static final long serialVersionUID = 1L;

    /**
     * 是否完善信息（1：完善待审；2：已完善（即：完善通过）；3：完善未通过）
     */
    public static final String[] A_PERFECT_INFORMATION = {"1", "2", "3"};

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 工会id（sys_dept表id）
     */
    private Integer deptId;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 统一社会信用代码（组织机构代码）
     */
    private String unitOrgCode;
    /**
     * 法人和其他组织统一社会信用代码
     */
    private String othersOrgCode;
    /**
     * 单位地址
     */
    private String unitAddress;
    /**
     * 单位所在政区(unit_district)
     */
    private String unitDistrict;
    /**
     * 单位性质类别(unit_nature)
     */
    private String unitNature;
    /**
     * 经济类型(unit_economic_type)
     */
    private String unitEconomicType;
    /**
     * 单位所属行业(unit_industry)
     */
    private String unitIndustry;
    /**
     * 职工人数
     */
    private Integer unitNumber;
    /**
     * 上级工会id（sys_dept表id）
     */
    @NotNull(message = "上级工会为空")
    private Integer pId;
    /**
     * 上级工会名称
     */
    private String pName;
    /**
     * 工会简称
     */
    private String unionSimpleName;
    /**
     * 工会全称
     */
    private String unionName;
    /**
     * 工会类型
     */
    private String unionType;
    /**
     * 工会邮箱union_email
     */
    private String unionEmail;
    /**
     * 建会时间
     */
    private String createunionTime;
    /**
     * 会员人数
     */
    private Integer membership;
    /**
     * 工会负责人（主席、工委主任）
     */
    private String unionLeader;
    /**
     * 工会负责人电话
     */
    private String unionLeaderPhone;
    /**
     * 审核状态(1:待审核；2：审核通过；3:审核拒绝；)
     */
    private Integer status;
    /**
     * 数据来源（0：系统添加；1：申请建会；）
     */
    private Integer source;
    /**
     * 建会目的
     */
    private String comment;

    /**
     * 工会邮编
     */
    private String unitZipCode;
    /**
     * 专职干部人数
     */
    private Integer cadresNumber;
    /**
     * 主席名称
     */
    private String chairmanName;
    /**
     * 现任XX届主席
     */
    private String chairmanSession;
    /**
     * 主席手机号
     */
    private String chairmanMobile;

    /**
     * 是否完善信息（1：未完善；2：已完善（即：完善通过）；3：完善未通过）
     */
    private String perfectInformation;

    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
