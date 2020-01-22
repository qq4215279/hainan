package com.gobestsoft.common.modular.mst.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 申请建会企业信息补充表
 * @author ke
 *
 */
@Data
@TableName("mst_enterprise_ext")
public class EnterpriseExt extends Model<EnterpriseExt> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@TableId(value="extId",type=IdType.AUTO)
	private Integer extId;
	
	/**
	 * 组织ID
	 */
	private Integer deptId;
	
	/**
	 * 是否模范职工之家（0否 1是）
	 */
	private Integer staffHomeFlag;
	
	/**
	 * 职工之家级别
	 */
	private Integer staffHomeLevel;
	
	/**
	 * 是否双爱双评先进单位
	 */
	private Integer advancedFlag;
	
	/**
	 * 双爱双评先进单位级别
	 */
	private Integer advancedLevel;
	
	/**
	 * 涵盖单位
	 */
	private Integer company;
	
	/**
	 * 非公企业法人单位
	 */
	private Integer personCompany;
	
	/**
	 * 产业活动单位
	 */
	private Integer establishment;
	
	/**
	 * 社会组织
	 */
	private Integer socialOrganization;
	
	/**
	 * 职工数量
	 */
	private Integer employeeNumber;
	
	/**
	 * 非公企业法人单位职工
	 */
	private Integer personCompanyNumber;
	
	/**
	 * 产业活动单位职工
	 */
	private Integer establishmentNumber;
	
	/**
	 * 社会组织职工
	 */
	private Integer socialOrganizationNumber;
	
	/**
	 * 共有会员
	 */
	private Integer memberNumber;
	
	/**
	 * 非公企业法人单位会员
	 */
	private Integer personCompanyMemberNumber;
	
	/**
	 * 产业活动单位会员
	 */
	private Integer establishmentMemberNumber;
	
	/**
	 * 社会组织会员
	 */
	private Integer socialOrganizationMemberNumber;
	
	/**
	 * 选举产生形式
	 */
	private String committeeType;
	
	/**
	 * 本届任期开始时间
	 */
	private String tenureStartTime;
	
	/**
	 * 任期
	 */
	private Integer organizationTenure;
	
	/**
	 * 是否建立女职工委员会（0否 1是）
	 */
	private Integer femaleCommitteeFlag;
	
	/**
	 * 工会是否亮牌子（0否 1是）
	 */
	private Integer plateFlag;
	
	/**
	 * 是否建立经费审查委员会（0否 1是）
	 */
	private Integer fundsExaminationCommitteeFlag;
	
	/**
	 * 工会主席是否亮身份（0否 1是）
	 */
	private Integer chairmanFlag;
	
	/**
	 * 是否有工会印章（0否 1是）
	 */
	private Integer sealFlag;
	
	/**
	 * 是否有工会法人资格证（0否 1是）
	 */
	private Integer certificationFlag;
	
	/**
	 * 是否有工会组织机构代码（0否 1是）
	 */
	private Integer orgCodeFlag;
	
	/**
	 * 工会组织机构代码
	 */
	private String orgCode;
	
	/**
	 * 是否有工会单独经费帐户
	 */
	private Integer fundGuaranteeFlag;
	
	/**
	 * 经费数额（元/年）
	 */
	private String expenditure;
	
	/**
	 * 是否成立分会/小组（0否 1是）
	 */
	private Integer groupFlag;
	
	/**
	 * 分会小组个数
	 */
	private Integer groupNumber;
	
	/**
	 * 社会组织类型
	 */
	private String socialOrganizationType;
	
	/**
	 * 是否成立劳动争议调价委员会（0否 1是）
	 */
	private Integer solveFlag;
	
	/**
	 * 是否签订集体合同（0否 1是）
	 */
	private Integer contractFlag;
	
	/**
	 * 最近签订时间
	 */
	private String signTime;
	
	/**
	 * 是否报人社部门审查通过
	 */
	private Integer molssFlag;
	
	/**
	 * 集体合同有效期
	 */
	private Integer contractIndate;
	
	/**
	 * 集体合同签订次数
	 */
	private Integer contractNumber;
	
	/**
	 * 是否建立工资集体协商制度（0否 1是）
	 */
	private Integer negotiateFlag;
	
	/**
	 * 年度
	 */
	private String negotiateTime;
	
	/**
	 * 年度增幅
	 */
	private Integer amplification;
	
	/**
	 * 是否建立了职工代表大会制度（0否 1是）
	 */
	private Integer conferenceFlag;
	
	/**
	 * 职工代表大会制度届数
	 */
	private Integer conferencePeriod;
	
	/**
	 * 职工代表大会制度任期
	 */
	private Integer conferenceTenure;
	
	/**
	 * 最近召开时间
	 */
	private String convokeTime;
	
	/**
	 * 形式
	 */
	private String shape;
	
	/**
	 * 本届召开时间
	 */
	private String reigningTime;
	
	/**
	 * 是否实行了厂务公开
	 */
	private Integer factoryOpenFlag;
	
	/**
	 * 本届召开会员（代表）大会次数
	 */
    private Integer convokeNumber;
	
    private String createUser;
	
	private String createTime;
	
	private String updateUser;
	
	private String updateTime;
	
	@Override
	protected Serializable pkVal() {
		return this.extId;
	}

	@Override
	public String toString() {
		return "EnterpriseExt [extId=" + extId + ", deptId=" + deptId + ", staffHomeFlag=" + staffHomeFlag
				+ ", staffHomeLevel=" + staffHomeLevel + ", advancedFlag=" + advancedFlag + ", advancedLevel="
				+ advancedLevel + ", company=" + company + ", personCompany=" + personCompany + ", establishment="
				+ establishment + ", socialOrganization=" + socialOrganization + ", employeeNumber=" + employeeNumber
				+ ", personCompanyNumber=" + personCompanyNumber + ", establishmentNumber=" + establishmentNumber
				+ ", socialOrganizationNumber=" + socialOrganizationNumber + ", memberNumber=" + memberNumber
				+ ", personCompanyMemberNumber=" + personCompanyMemberNumber + ", establishmentMemberNumber="
				+ establishmentMemberNumber + ", socialOrganizationMemberNumber=" + socialOrganizationMemberNumber
				+ ", committeeType=" + committeeType + ", tenureStartTime=" + tenureStartTime + ", organizationTenure="
				+ organizationTenure + ", femaleCommitteeFlag=" + femaleCommitteeFlag + ", plateFlag=" + plateFlag
				+ ", fundsExaminationCommitteeFlag=" + fundsExaminationCommitteeFlag + ", chairmanFlag=" + chairmanFlag
				+ ", sealFlag=" + sealFlag + ", certificationFlag=" + certificationFlag + ", orgCodeFlag=" + orgCodeFlag
				+ ", orgCode=" + orgCode + ", fundGuaranteeFlag=" + fundGuaranteeFlag + ", expenditure=" + expenditure
				+ ", groupFlag=" + groupFlag + ", groupNumber=" + groupNumber + ", socialOrganizationType="
				+ socialOrganizationType + ", solveFlag=" + solveFlag + ", contractFlag=" + contractFlag + ", signTime="
				+ signTime + ", molssFlag=" + molssFlag + ", contractIndate=" + contractIndate + ", contractNumber="
				+ contractNumber + ", negotiateFlag=" + negotiateFlag + ", negotiateTime=" + negotiateTime
				+ ", amplification=" + amplification + ", conferenceFlag=" + conferenceFlag + ", conferencePeriod="
				+ conferencePeriod + ", conferenceTenure=" + conferenceTenure + ", convokeTime=" + convokeTime
				+ ", shape=" + shape + ", reigningTime=" + reigningTime + ", factoryOpenFlag=" + factoryOpenFlag
				+ ", convokeNumber=" + convokeNumber + ", createUser=" + createUser + ", createTime=" + createTime
				+ ", updateUser=" + updateUser + ", updateTime=" + updateTime + "]";
	}

	
	
}
