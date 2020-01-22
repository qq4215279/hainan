package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 法律援助log表
 *
 * @author li
: * @date 2018-9-06 
 */
@TableName("srv_law_consultation_log")
@Data
public class SrvLawConsultationLogPojo extends Model<SrvLawConsultationLogPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    
    /**
     *  ID
     */
    private Integer id;
    
    private Integer status;

    private Integer consultationId;
    
    private Integer answerDeptId;
    
    private Integer transferDeptId;
    
    private String transferReason;

    private String transferTime;

}
