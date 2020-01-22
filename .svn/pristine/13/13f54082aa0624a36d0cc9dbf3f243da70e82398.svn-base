package com.gobestsoft.api.modular.srv.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * create by li
 * on 2018/9/5 下午4:23
 */
@Data
public class LawConsultParam {
	
    private Integer status;

    @NotEmpty(message = "咨询类型必选")
    private String category;
    
    @NotEmpty(message = "咨询内容不能为空")
    private String content;

    private String auid;

    private String createTime;

    private String replyUid;

    private String replyTime;
    
    private Integer type;
}
