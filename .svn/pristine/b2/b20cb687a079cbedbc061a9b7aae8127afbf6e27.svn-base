package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.gobestsoft.common.modular.cms.model.Article;

import lombok.Data;

/**
 * <p>
 * 	民主管理表
 * <p>
 * @author ld
 *
 */

@Data
@TableName("org_democracy")
public class DemoCarry extends Model<DemoCarry>{
	
	private static final long serialVersionUID = 1L;

	private String democracyId;
	
	private String deptNo;
	
	private String initDate;
	
	private String createTime;
	
	private String isDemocracyConsultative;
	
	private String isWorkersCongress;


	@Override
	protected Serializable pkVal() {
		return this.democracyId;
	}


	@Override
	public String toString() {
		return "DemoCarry [democracyId=" + democracyId + ", deptNo=" + deptNo + ", initDate=" + initDate
				+ ", createTime=" + createTime + ", isDemocracyConsultative=" + isDemocracyConsultative
				+ ", isWorkersCongress=" + isWorkersCongress + "]";
	}

	
}
