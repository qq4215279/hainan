package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gobestsoft.core.util.DateUtil;
import lombok.Data;

@Data
@TableName("member_del_record")
/**
 * 会员删除记录
 */
public class MemberDelRecordPojo {

    private Integer id;
     private String name;
     private String mobile;
     private String certificate_num;
     private Integer dept_id;
     private String dept_name;
     private String operation_time;
     private String operation_user;

}
