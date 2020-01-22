package com.gobestsoft.common.constant;

/**
 * 字典GROUP_CODE常量集合
 * 
 * @author zhangdaowei
 * @date 2018-08-23 13点07分
 */
public class DictGroupCodeConstants {

	/**
	 * 人员基本信息表 person_info中涉及到字典项group_code （11个）
	 */
	public static final String[] PERSON_INFO_GROUP_CODES = { "lib_sex" // 性别
			, "lib_ethnic_group" // 民族
			, "lib_work_situation" // 就业情况
			, "lib_identification_type" // 证件类型
			, "lib_education_code" // 学历
			, "lib_technology" // 技能等级
			, "lib_household" // 户籍类型
			, "lib_member_change" // 会籍变化类型
			, "lib_member_change_reason" // 会籍变化原因
			, "lib_political_status" // 政治面貌
			, "lib_is_not" // 是否农民工会员
	};
	
	/**
	 * 工会组织信息表dept_organization中涉及到的字典项group_code （4个）
	 */
	public static final String[] ORGANIZATION_GROUP_CODES = { "lib_base_org_type" // 基层工会类型
			, "lib_unit_type" //单位性质类别
			, "lib_economic_type" //经济类型
			, "lib_industry_type" //单位所属行业    
	};

}
