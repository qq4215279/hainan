package com.gobestsoft.rabbitmq.processor;


import lombok.Data;

import java.io.InputStream;

/**
 * 会员导入的队列对象
 */
@Data
public class MemberImportProcessor extends  AbstractProcessor{


    private static final long serialVersionUID = 5519735700173005335L;

    private String createUid;
    private Integer deptId;
    private Integer logId;
    private Integer type;

    public MemberImportProcessor(Integer logId, Integer type, String createUid, Integer deptId) {
        this.logId = logId;
        this.type = type;
        this.createUid = createUid;
        this.deptId = deptId;
    }
}
