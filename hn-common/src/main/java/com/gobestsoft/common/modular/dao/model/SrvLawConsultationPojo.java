package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * Banner管理表
 *
 * @author caoy
 * @date 2018-02-09 14:18:32
 */
@TableName("srv_law_consultation")
@Data
public class SrvLawConsultationPojo extends Model<SrvLawConsultationPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * Banner ID
     */
    private Integer id;
    
    private Integer status;

    private String category;

    private String content;

    private String auid;

    private String createTime;
    
    private String replyContent;

    private String replyUid;

    private String replyTime;
    
    private Integer type;

}
